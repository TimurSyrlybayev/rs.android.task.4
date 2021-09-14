package com.example.rsschooltask4

import android.app.Application
import com.example.rsschooltask4.data.CursorRepository
import com.example.rsschooltask4.data.RoomRepository

class TaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RoomRepository.initialize(this)
        CursorRepository.initialize(this)
    }
}