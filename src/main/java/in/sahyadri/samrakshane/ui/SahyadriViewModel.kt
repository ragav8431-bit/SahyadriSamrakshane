package `in`.sahyadri.samrakshane.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.sahyadri.samrakshane.data.repository.AlertRepository
import `in`.sahyadri.samrakshane.data.repository.GeminiClassifier
import `in`.sahyadri.samrakshane.data.repository.LocationProvider
import `in`.sahyadri.samrakshane.domain.AlertType
import `in`.sahyadri.samrakshane.domain.EcoAlert
import `in`.sahyadri.samrakshane.domain.LocationPoint
import `in`.sahyadri.samrakshane.domain.ReportDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ReportUiState(
    val selectedType: AlertType = AlertType.ForestFire,
    val description: String = "",
    val location: LocationPoint = LocationPoint(13.3667, 75.7833),
    val photoCaptured: Boolean = false,
    val aiSuggestion: String = "",
    val lastSubmitted: EcoAlert? = null
)

data class HomeUiState(
    val alerts: List<EcoAlert> = emptyList(),
    val report: ReportUiState = ReportUiState(),
    val online: Boolean = true
)

class SahyadriViewModel(
    private val repository: AlertRepository,
    locationProvider: LocationProvider,
    private val classifier: GeminiClassifier
) : ViewModel() {
    private val reportState = MutableStateFlow(ReportUiState())

    val uiState: StateFlow<HomeUiState> = combine(
        repository.alerts,
        reportState,
        locationProvider.liveLocation()
    ) { alerts, report, location ->
        HomeUiState(alerts = alerts, report = report.copy(location = location), online = true)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())

    fun selectType(type: AlertType) {
        reportState.value = reportState.value.copy(selectedType = type)
    }

    fun updateDescription(value: String) {
        reportState.value = reportState.value.copy(description = value)
    }

    fun capturePhoto() {
        val selected = reportState.value.selectedType
        reportState.value = reportState.value.copy(
            photoCaptured = true,
            aiSuggestion = classifier.suggest(selected)
        )
    }

    fun submit() {
        val report = reportState.value
        viewModelScope.launch {
            val submitted = repository.submit(
                ReportDraft(
                    type = report.selectedType,
                    description = report.description.ifBlank { "Citizen alert submitted from field app." },
                    photoPath = if (report.photoCaptured) "local://captured-alert.jpg" else null,
                    location = report.location,
                    aiSuggestion = report.aiSuggestion.ifBlank { classifier.suggest(report.selectedType) }
                )
            )
            reportState.value = ReportUiState(lastSubmitted = submitted)
        }
    }

    fun retrySync() {
        viewModelScope.launch { repository.syncPending() }
    }
}
