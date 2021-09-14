package com.example.rsschooltask4.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.rsschooltask4.data.CursorRepository
import com.example.rsschooltask4.data.RoomRepository
import com.example.rsschooltask4.data.model.ItemData
import com.example.rsschooltask4.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(context: Context) : ViewModel() {

    private val roomIsOn by lazy {
    val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.getBoolean("cursor-room_switch", false)
    }

    private val sortParameter by lazy {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.getString("list_preference", "id")
    }

    private val roomRepository = RoomRepository.get()
    private val cursorRepository = CursorRepository.get()

//    lateinit var list: MutableLiveData<MutableList<ItemData>>

    fun addRecord(itemData: ItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            if (roomIsOn) roomRepository.addItem(itemData) else cursorRepository.addItem(itemData)
        }
    }

    val list = if (roomIsOn) roomRepository.readTable(sortParameter) else cursorRepository.readTable(sortParameter)

//    fun readRecords(): MutableLiveData<MutableList<ItemData>> {
//        viewModelScope.launch(Dispatchers.IO) {
//            list = roomRepository.readTable()
//        }
//        return list
//    }

    fun updateRecord(
        id: Long,
        firstParameter: String,
        secondParameter: String,
        thirdParameter: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (roomIsOn) {
                roomRepository.updateItem(id, firstParameter, secondParameter, thirdParameter)
            } else {
                cursorRepository.updateItem(id, firstParameter, secondParameter, thirdParameter)
            }
        }
    }

    fun deleteRecord(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (roomIsOn) roomRepository.deleteItem(id) else cursorRepository.deleteItem(id)
        }
    }
}