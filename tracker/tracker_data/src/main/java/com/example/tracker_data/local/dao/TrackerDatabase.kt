package com.example.tracker_data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tracker_data.local.entity.TrackedFoodEntity

@Database(
    entities = [TrackedFoodEntity::class],
    version = 1
)
abstract class TrackerDatabase : RoomDatabase() {

    abstract val dao: TrackerDao
}