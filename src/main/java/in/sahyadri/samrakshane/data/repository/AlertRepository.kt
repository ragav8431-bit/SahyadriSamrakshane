package `in`.sahyadri.samrakshane.data.repository

import `in`.sahyadri.samrakshane.domain.EcoAlert
import `in`.sahyadri.samrakshane.domain.ReportDraft
import kotlinx.coroutines.flow.Flow

interface AlertRepository {
    val alerts: Flow<List<EcoAlert>>
    suspend fun submit(draft: ReportDraft): EcoAlert
    suspend fun syncPending()
}
