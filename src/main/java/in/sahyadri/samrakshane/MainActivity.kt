package `in`.sahyadri.samrakshane

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import `in`.sahyadri.samrakshane.ui.SahyadriViewModel
import `in`.sahyadri.samrakshane.ui.screen.SahyadriAppScreen
import `in`.sahyadri.samrakshane.ui.theme.SahyadriTheme

class MainActivity : ComponentActivity() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    private val viewModel by viewModels<SahyadriViewModel> {
        val app = application as SahyadriApp
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SahyadriViewModel(app.repository, app.locationProvider, app.classifier) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher.launch(
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
        )
        setContent {
            SahyadriTheme {
                SahyadriAppScreen(viewModel = viewModel)
            }
        }
    }
}
