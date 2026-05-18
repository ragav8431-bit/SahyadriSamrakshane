package `in`.sahyadri.samrakshane.data.repository

import `in`.sahyadri.samrakshane.data.local.AlertDao
import `in`.sahyadri.samrakshane.data.local.toEntity
import `in`.sahyadri.samrakshane.domain.AlertStatus
import `in`.sahyadri.samrakshane.domain.EcoAlert
import `in`.sahyadri.samrakshane.domain.ReportDraft
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import java.util.UUID

class RoomAlertRepository(
    private val dao: AlertDao
) : AlertRepository {
    override val alerts = dao.observeAlerts().map { rows -> rows.map { it.toDomain() } }

    override suspend fun submit(draft: ReportDraft): EcoAlert {
        val alert = EcoAlert(
            id = "SSR-${UUID.randomUUID().toString().take(8).uppercase()}",
            type = draft.type,
            status = AlertStatus.Reported,
            description = draft.description,
            aiSuggestion = draft.aiSuggestion,
            photoPath = draft.photoPath,
            location = draft.location,
            createdAtMillis = System.currentTimeMillis(),
            synced = false
        )
        dao.upsert(alert.toEntity())
        syncPending()
        return alert
    }

    override suspend fun syncPending() {
        dao.pendingAlerts().forEach { pending ->
            delay(150)
            dao.upsert(pending.copy(synced = true))
        }
    }
}
