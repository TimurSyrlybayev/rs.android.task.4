package com.example.rsschooltask4.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.rsschooltask4.data.db.TaskDatabase
import com.example.rsschooltask4.data.model.DATABASE_NAME
import com.example.rsschooltask4.data.model.ItemData
import java.lang.IllegalStateException

class RoomRepository private constructor(context: Context) : RepositoryInterface {

    private val database = Room.databaseBuilder(
        context.applicationContext,
        TaskDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val taskDao = database.taskDao()

    override suspend fun addItem(itemData: ItemData) {
        taskDao.createItemRoom(itemData)
    }

    fun readTable(sortParameter: String?): LiveData<MutableList<ItemData>> {
        return when (sortParameter) {
            "first_parameter_asc" -> taskDao.readTableRoomFirstParameterAsc()
            "first_parameter_desc" -> taskDao.readTableRoomFirstParameterDesc()
            "second_parameter_asc" -> taskDao.readTableRoomSecondParameterAsc()
            "second_parameter_desc" -> taskDao.readTableRoomSecondParameterDesc()
            "third_parameter_asc" -> taskDao.readTableRoomThirdParameterAsc()
            "third_parameter_desc" -> taskDao.readTableRoomThirdParameterDesc()
            else -> taskDao.readTableRoomInitialOrder()
        }
    }

    override suspend fun updateItem(
        id: Long,
        firstParameter: String,
        secondParameter: String,
        thirdParameter: String,
    ) {
        if (firstParameter.isNotEmpty()) taskDao.updateFirstParameterRoom(id, firstParameter)
        if (secondParameter.isNotEmpty()) taskDao.updateSecondParameterRoom(id, secondParameter)
        if (thirdParameter.isNotEmpty()) taskDao.updateThirdParameterRoom(id, thirdParameter)
    }

    override suspend fun deleteItem(id: Long) {
        taskDao.deleteItemRoom(id)
    }

    companion object {
        private var INSTANCE: RoomRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = RoomRepository(context)
            }
        }

        fun get(): RoomRepository {
            return INSTANCE ?: throw IllegalStateException("Repository is not initialized")
        }
    }
}