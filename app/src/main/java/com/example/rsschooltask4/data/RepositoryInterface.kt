package com.example.rsschooltask4.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rsschooltask4.data.model.ItemData

interface RepositoryInterface {
    suspend fun addItem(itemData: ItemData)

//    fun readTable(): LiveData<MutableList<ItemData>>

    suspend fun updateItem(id: Long, firstParameter: String, secondParameter: String, thirdParameter: String)

    suspend fun deleteItem(id: Long)
}