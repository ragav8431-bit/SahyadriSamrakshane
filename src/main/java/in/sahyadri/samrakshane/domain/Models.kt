package `in`.sahyadri.samrakshane.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.ContentCut

enum class AlertType(
    val label: String,
    val color: Color,
    val icon: ImageVector
) {
    ForestFire("Forest Fire", Color(0xFFB4472E), Icons.Filled.LocalFireDepartment),
    Landslide("Landslide", Color(0xFF8A6F3D), Icons.Filled.Terrain),
    IllegalTreeCutting("Illegal Tree Cutting", Color(0xFF4D774E), Icons.Filled.ContentCut),
    WildlifeSighting("Wildlife Sighting", Color(0xFF78623A), Icons.Filled.Pets);
}

enum class AlertStatus(val label: String) {
    Reported("Reported"),
    Verified("Verified"),
    TeamDispatched("Team Dispatched")
}

data class LocationPoint(
    val latitude: Double,
    val longitude: Double
) {
    fun display(): String = "%.5f, %.5f".format(latitude, longitude)
}

data class EcoAlert(
    val id: String,
    val type: AlertType,
    val status: AlertStatus,
    val description: String,
    val aiSuggestion: String,
    val photoPath: String?,
    val location: LocationPoint,
    val createdAtMillis: Long,
    val synced: Boolean
)

data class ReportDraft(
    val type: AlertType,
    val description: String,
    val photoPath: String?,
    val location: LocationPoint,
    val aiSuggestion: String
)

val EcoTips = listOf(
    "Stay on marked forest trails and avoid shortcuts through fragile undergrowth.",
    "Report smoke, fresh stumps, landslide cracks, or trapped wildlife immediately.",
    "Do not light campfires in dry grass, leaf litter, or windy conditions.",
    "Carry back all plastic, food wrappers, and batteries from eco-sensitive zones.",
    "Keep distance from wildlife and never feed animals near roads or villages.",
    "Avoid loud music in forests; sound disrupts nesting and movement patterns.",
    "During monsoon treks, avoid slopes with fresh cracks, tilted trees, or seepage.",
    "Share GPS coordinates with forest staff when reporting an incident.",
    "Use refillable bottles and avoid single-use plastics near streams.",
    "Respect temporary forest closures during fire risk or wildlife movement periods."
)
