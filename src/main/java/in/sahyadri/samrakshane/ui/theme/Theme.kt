package `in`.sahyadri.samrakshane.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ForestScheme = lightColorScheme(
    primary = Color(0xFF214D2B),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC9E8CB),
    onPrimaryContainer = Color(0xFF08210E),
    secondary = Color(0xFF6C5A2F),
    onSecondary = Color.White,
    tertiary = Color(0xFF9F5B2F),
    background = Color(0xFFF5F1E8),
    onBackground = Color(0xFF1D231B),
    surface = Color(0xFFFFFBF1),
    onSurface = Color(0xFF1D231B),
    surfaceVariant = Color(0xFFE1D9C7),
    onSurfaceVariant = Color(0xFF484333),
    error = Color(0xFFB3261E)
)

@Composable
fun SahyadriTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ForestScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
