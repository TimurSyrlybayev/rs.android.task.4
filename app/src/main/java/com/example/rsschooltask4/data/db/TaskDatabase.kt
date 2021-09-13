package com.example.rsschooltask4.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rsschooltask4.data.model.ItemData

@Database(entities = [ItemData::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}