package com.example.rsschooltask4

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "task4database.db"
const val TABLE_NAME = "task4items"
const val KEY_ID = "itemid"
const val KEY_FIRST_PARAMETER = "itemfirstparameter"
const val KEY_SECOND_PARAMETER = "itemsecondparameter"
const val KEY_THIRD_PARAMETER = "itemthirdparameter"

data class ItemData(
    val id: Long,
    val first_parameter: String,
    val second_parameter: String,
    val third_parameter: String,
)
