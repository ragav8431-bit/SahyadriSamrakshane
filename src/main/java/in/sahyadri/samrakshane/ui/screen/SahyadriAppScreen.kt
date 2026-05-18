package `in`.sahyadri.samrakshane.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import `in`.sahyadri.samrakshane.domain.AlertStatus
import `in`.sahyadri.samrakshane.domain.AlertType
import `in`.sahyadri.samrakshane.domain.EcoAlert
import `in`.sahyadri.samrakshane.domain.EcoTips
import `in`.sahyadri.samrakshane.ui.SahyadriViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SahyadriAppScreen(viewModel: SahyadriViewModel) {
    val state by viewModel.uiState.collectAsState()
    var tab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf("Home", "Report", "Learn").forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = tab == index,
                        onClick = { tab = index },
                        icon = { Icon(if (index == 1) Icons.Default.CameraAlt else Icons.Default.Info, null) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (tab) {
                0 -> HomeScreen(state.alerts, state.online, viewModel::retrySync) { tab = 1 }
                1 -> ReportScreen(
                    state.report.selectedType,
                    state.report.description,
                    state.report.location.display(),
                    state.report.photoCaptured,
                    state.report.aiSuggestion,
                    viewModel::selectType,
                    viewModel::updateDescription,
                    viewModel::capturePhoto,
                    viewModel::submit
                )
                else -> EducationScreen()
            }
        }
    }
}

@Composable
private fun HomeScreen(
    alerts: List<EcoAlert>,
    online: Boolean,
    onSync: () -> Unit,
    onReport: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            HeaderBand(alerts, online, onSync, onReport)
        }
        item {
            Text("Alert history", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        if (alerts.isEmpty()) {
            item { EmptyHistory() }
        } else {
            items(alerts, key = { it.id }) { AlertRow(it) }
        }
    }
}

@Composable
private fun HeaderBand(alerts: List<EcoAlert>, online: Boolean, onSync: () -> Unit, onReport: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF214D2B), Color(0xFF526B35), Color(0xFF8A6F3D))
                )
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Sahyadri Samrakshane", color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Protect the Forest. Protect the Future.", color = Color(0xFFF2E8C8), style = MaterialTheme.typography.bodyLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            AssistChip(
                onClick = onSync,
                leadingIcon = { Icon(if (online) Icons.Default.CloudDone else Icons.Default.CloudOff, null) },
                label = { Text(if (online) "Online sync ready" else "Offline queue active") }
            )
            AssistChip(onClick = {}, label = { Text("${alerts.count { !it.synced }} pending") })
        }
        Button(onClick = onReport) {
            Icon(Icons.Default.Send, null)
            Spacer(Modifier.size(8.dp))
            Text("Quick report")
        }
    }
}

@Composable
private fun ReportScreen(
    selectedType: AlertType,
    description: String,
    location: String,
    photoCaptured: Boolean,
    aiSuggestion: String,
    onType: (AlertType) -> Unit,
    onDescription: (String) -> Unit,
    onCapture: () -> Unit,
    onSubmit: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Report ecological alert", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Capture field evidence with live GPS and offline-first submission.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                AlertType.entries.take(2).forEach { type -> AlertTypeChip(type, selectedType == type, onType) }
            }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                AlertType.entries.drop(2).forEach { type -> AlertTypeChip(type, selectedType == type, onType) }
            }
        }
        item {
            CameraCapturePanel(photoCaptured, onCapture)
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.MyLocation, null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.size(10.dp))
                    Column {
                        Text("Live GPS lock", fontWeight = FontWeight.Bold)
                        Text(location)
                    }
                }
            }
        }
        item {
            OutlinedTextField(
                value = description,
                onValueChange = onDescription,
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                label = { Text("Optional field notes") }
            )
        }
        if (aiSuggestion.isNotBlank()) {
            item {
                Card(border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)) {
                    Text(aiSuggestion, modifier = Modifier.padding(14.dp))
                }
            }
        }
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = photoCaptured,
                onClick = onSubmit
            ) {
                Icon(Icons.Default.CheckCircle, null)
                Spacer(Modifier.size(8.dp))
                Text("Submit alert")
            }
        }
    }
}

@Composable
private fun AlertTypeChip(type: AlertType, selected: Boolean, onType: (AlertType) -> Unit) {
    FilterChip(
        modifier = Modifier,
        selected = selected,
        onClick = { onType(type) },
        leadingIcon = { Icon(type.icon, null, tint = type.color) },
        label = { Text(type.label) }
    )
}

@Composable
private fun CameraCapturePanel(photoCaptured: Boolean, onCapture: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1F2D20)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.CameraAlt, null, tint = Color(0xFFD8B56D), modifier = Modifier.size(48.dp))
                Text(
                    if (photoCaptured) "Photo captured with current GPS lock" else "CameraX capture area",
                    color = Color(0xFFF6EFD9)
                )
            }
        }
        ElevatedButton(onClick = onCapture, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Default.CameraAlt, null)
            Spacer(Modifier.size(8.dp))
            Text(if (photoCaptured) "Retake photo" else "Capture photo")
        }
    }
}

@Composable
private fun EducationScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Eco-zone guide", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
        items(EcoTips) { tip ->
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Text(tip, modifier = Modifier.padding(14.dp), style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun AlertRow(alert: EcoAlert) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(alert.type.icon, null, tint = alert.type.color, modifier = Modifier.size(36.dp))
            Spacer(Modifier.size(12.dp))
            Column(Modifier.weight(1f)) {
                Text(alert.type.label, fontWeight = FontWeight.Bold)
                Text("${alert.status.label} • ${alert.location.display()}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(formatDate(alert.createdAtMillis), style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = {}) {
                Icon(if (alert.synced) Icons.Default.CloudDone else Icons.Default.CloudOff, null)
            }
        }
    }
}

@Composable
private fun EmptyHistory() {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Text(
            "No alerts submitted yet. Start a report when you spot fire, landslide risk, tree cutting, or wildlife threat.",
            modifier = Modifier.padding(16.dp)
        )
    }
}

private fun formatDate(millis: Long): String {
    return SimpleDateFormat("dd MMM yyyy, h:mm a", Locale.getDefault()).format(Date(millis))
}
