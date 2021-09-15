package com.example.rsschooltask4.data

import com.example.rsschooltask4.data.model.ItemData

interface RepositoryInterface {
    suspend fun addItem(itemData: ItemData)

    suspend fun updateItem(
        id: Long,
        firstParameter: String,
        secondParameter: String,
        thirdParameter: String
    )

    suspend fun deleteItem(id: Long)
}