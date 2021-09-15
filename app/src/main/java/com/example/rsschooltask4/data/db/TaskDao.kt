package com.example.rsschooltask4.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rsschooltask4.data.model.*

@Dao
interface TaskDao {
    @Insert
    fun createItemRoom(itemData: ItemData)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $KEY_ID")
    fun readTableRoomInitialOrder(): LiveData<MutableList<ItemData>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $KEY_FIRST_PARAMETER COLLATE NOCASE ASC")
    fun readTableRoomFirstParameterAsc(): LiveData<MutableList<ItemData>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $KEY_FIRST_PARAMETER COLLATE NOCASE DESC")
    fun readTableRoomFirstParameterDesc(): LiveData<MutableList<ItemData>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $KEY_SECOND_PARAMETER COLLATE NOCASE ASC")
    fun readTableRoomSecondParameterAsc(): LiveData<MutableList<ItemData>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $KEY_SECOND_PARAMETER COLLATE NOCASE DESC")
    fun readTableRoomSecondParameterDesc(): LiveData<MutableList<ItemData>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $KEY_THIRD_PARAMETER COLLATE NOCASE ASC")
    fun readTableRoomThirdParameterAsc(): LiveData<MutableList<ItemData>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $KEY_THIRD_PARAMETER COLLATE NOCASE DESC")
    fun readTableRoomThirdParameterDesc(): LiveData<MutableList<ItemData>>

    @Query("UPDATE $TABLE_NAME SET $KEY_FIRST_PARAMETER=:firstParameter WHERE $KEY_ID=:id")
    fun updateFirstParameterRoom(id: Long, firstParameter: String)

    @Query("UPDATE $TABLE_NAME SET $KEY_SECOND_PARAMETER=:secondParameter WHERE $KEY_ID=:id")
    fun updateSecondParameterRoom(id: Long, secondParameter: String)

    @Query("UPDATE $TABLE_NAME SET $KEY_THIRD_PARAMETER=:thirdParameter WHERE $KEY_ID=:id")
    fun updateThirdParameterRoom(id: Long, thirdParameter: String)

    @Query("DELETE FROM $TABLE_NAME WHERE $KEY_ID=:id")
    fun deleteItemRoom(id: Long)
}