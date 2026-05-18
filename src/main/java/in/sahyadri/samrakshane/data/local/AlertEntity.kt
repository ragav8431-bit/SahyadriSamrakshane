package `in`.sahyadri.samrakshane.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import `in`.sahyadri.samrakshane.domain.AlertStatus
import `in`.sahyadri.samrakshane.domain.AlertType
import `in`.sahyadri.samrakshane.domain.EcoAlert
import `in`.sahyadri.samrakshane.domain.LocationPoint

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey val id: String,
    val type: String,
    val status: String,
    val description: String,
    val aiSuggestion: String,
    val photoPath: String?,
    val latitude: Double,
    val longitude: Double,
    val createdAtMillis: Long,
    val synced: Boolean
) {
    fun toDomain() = EcoAlert(
        id = id,
        type = AlertType.valueOf(type),
        status = AlertStatus.valueOf(status),
        description = description,
        aiSuggestion = aiSuggestion,
        photoPath = photoPath,
        location = LocationPoint(latitude, longitude),
        createdAtMillis = createdAtMillis,
        synced = synced
    )
}

fun EcoAlert.toEntity() = AlertEntity(
    id = id,
    type = type.name,
    status = status.name,
    description = description,
    aiSuggestion = aiSuggestion,
    photoPath = photoPath,
    latitude = location.latitude,
    longitude = location.longitude,
    createdAtMillis = createdAtMillis,
    synced = synced
)
