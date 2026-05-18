package `in`.sahyadri.samrakshane.data.repository

import `in`.sahyadri.samrakshane.domain.LocationPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

class LocationProvider {
    fun liveLocation(): Flow<LocationPoint> = flow {
        var latitude = 13.3667
        var longitude = 75.7833
        while (true) {
            emit(LocationPoint(latitude, longitude))
            latitude += 0.00011
            longitude += 0.00008
            delay(2_000)
        }
    }
}
