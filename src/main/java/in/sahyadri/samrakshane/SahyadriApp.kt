package `in`.sahyadri.samrakshane

import android.app.Application
import androidx.room.Room
import `in`.sahyadri.samrakshane.data.local.SahyadriDatabase
import `in`.sahyadri.samrakshane.data.repository.GeminiClassifier
import `in`.sahyadri.samrakshane.data.repository.LocationProvider
import `in`.sahyadri.samrakshane.data.repository.RoomAlertRepository

class SahyadriApp : Application() {
    val database by lazy {
        Room.databaseBuilder(this, SahyadriDatabase::class.java, "sahyadri-alerts.db")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    val repository by lazy { RoomAlertRepository(database.alertDao()) }
    val locationProvider by lazy { LocationProvider() }
    val classifier by lazy { GeminiClassifier() }
}
