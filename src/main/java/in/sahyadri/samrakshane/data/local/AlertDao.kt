package `in`.sahyadri.samrakshane.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Query("SELECT * FROM alerts ORDER BY createdAtMillis DESC")
    fun observeAlerts(): Flow<List<AlertEntity>>

    @Query("SELECT * FROM alerts WHERE synced = 0 ORDER BY createdAtMillis ASC")
    suspend fun pendingAlerts(): List<AlertEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(alert: AlertEntity)
}
