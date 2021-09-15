package com.example.rsschooltask4.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rsschooltask4.data.model.*
import java.lang.IllegalStateException

class CursorRepository(context: Context) :
    RepositoryInterface,
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private var liveDataList: MutableLiveData<MutableList<ItemData>> = MutableLiveData()
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
            $KEY_ID INTEGER NOT NULL PRIMARY KEY,
            $KEY_FIRST_PARAMETER TEXT NOT NULL,
            $KEY_SECOND_PARAMETER TEXT NOT NULL,
            $KEY_THIRD_PARAMETER TEXT NOT NULL);
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    override suspend fun addItem(itemData: ItemData) {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put(KEY_FIRST_PARAMETER, itemData.first_parameter)
        values.put(KEY_SECOND_PARAMETER, itemData.second_parameter)
        values.put(KEY_THIRD_PARAMETER, itemData.third_parameter)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun readTable(sortParameter: String?): LiveData<MutableList<ItemData>> {
        val db: SQLiteDatabase = readableDatabase
        val list = mutableListOf<ItemData>()
        val selectAll: String = when (sortParameter) {
            "first_parameter_asc" -> "SELECT * FROM $TABLE_NAME ORDER BY $KEY_FIRST_PARAMETER COLLATE NOCASE ASC"
            "first_parameter_desc" -> "SELECT * FROM $TABLE_NAME ORDER BY $KEY_FIRST_PARAMETER COLLATE NOCASE DESC"
            "second_parameter_asc" -> "SELECT * FROM $TABLE_NAME ORDER BY $KEY_SECOND_PARAMETER COLLATE NOCASE ASC"
            "second_parameter_desc" -> "SELECT * FROM $TABLE_NAME ORDER BY $KEY_SECOND_PARAMETER COLLATE NOCASE DESC"
            "third_parameter_asc" -> "SELECT * FROM $TABLE_NAME ORDER BY $KEY_THIRD_PARAMETER COLLATE NOCASE ASC"
            "third_parameter_desc" -> "SELECT * FROM $TABLE_NAME ORDER BY $KEY_THIRD_PARAMETER COLLATE NOCASE DESC"
            else -> "SELECT * FROM $TABLE_NAME ORDER BY $KEY_ID"
        }
        val cursor: Cursor = db.rawQuery(selectAll, null)

        with(cursor) {
            if (moveToFirst()) {
                do {
                    val itemData = ItemData(
                        getLong(getColumnIndex(KEY_ID)),
                        getString(getColumnIndex(KEY_FIRST_PARAMETER)),
                        getString(getColumnIndex(KEY_SECOND_PARAMETER)),
                        getString(getColumnIndex(KEY_THIRD_PARAMETER)),
                    )
                    list.add(itemData)
                } while (moveToNext())
            }
            close()
            liveDataList.postValue(list)
            return liveDataList
        }
    }

    override suspend fun updateItem(
        id: Long,
        firstParameter: String,
        secondParameter: String,
        thirdParameter: String
    ) {
        val db:SQLiteDatabase = writableDatabase
        val values = ContentValues()

        if (firstParameter.isNotEmpty()) values.put(KEY_FIRST_PARAMETER, firstParameter)
        if (secondParameter.isNotEmpty()) values.put(KEY_SECOND_PARAMETER, secondParameter)
        if (thirdParameter.isNotEmpty()) values.put(KEY_THIRD_PARAMETER, thirdParameter)

        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                !values.isEmpty
            } else {
                !values.equals("")
            }
        ) db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(id.toString()))
    }

    override suspend fun deleteItem(id: Long) {
        val db:SQLiteDatabase = writableDatabase

        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
    }

    companion object {
        private var INSTANCE: CursorRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CursorRepository(context)
            }
        }

        fun get(): CursorRepository {
            return INSTANCE ?: throw IllegalStateException("Repository is not initialized")
        }
    }
}