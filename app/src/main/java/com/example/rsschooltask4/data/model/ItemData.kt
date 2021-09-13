package com.example.rsschooltask4.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "task4database.db"
const val TABLE_NAME = "task4items"
const val KEY_ID = "itemid"
const val KEY_FIRST_PARAMETER = "itemfirstparameter"
const val KEY_SECOND_PARAMETER = "itemsecondparameter"
const val KEY_THIRD_PARAMETER = "itemthirdparameter"

@Entity(tableName = TABLE_NAME)
data class ItemData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = KEY_ID)
    val id: Long,
    @ColumnInfo(name = KEY_FIRST_PARAMETER)
    val first_parameter: String,
    @ColumnInfo(name = KEY_SECOND_PARAMETER)
    val second_parameter: String,
    @ColumnInfo(name = KEY_THIRD_PARAMETER)
    val third_parameter: String,
)
