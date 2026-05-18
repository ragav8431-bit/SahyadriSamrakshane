package `in`.sahyadri.samrakshane.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AlertEntity::class], version = 1, exportSchema = true)
abstract class SahyadriDatabase : RoomDatabase() {
    abstract fun alertDao(): AlertDao
}
