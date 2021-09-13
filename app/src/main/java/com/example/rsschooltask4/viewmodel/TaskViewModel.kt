package com.example.rsschooltask4.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rsschooltask4.data.RoomRepository
import com.example.rsschooltask4.data.model.ItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val roomRepository = RoomRepository.get()
//    lateinit var list: MutableLiveData<MutableList<ItemData>>

    fun addRecord(itemData: ItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.addItem(itemData)
        }
    }

    val list = roomRepository.readTable()

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
            roomRepository.updateItem(id, firstParameter, secondParameter, thirdParameter)
        }
    }

    fun deleteRecord(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.deleteItem(id)
        }
    }
}