package com.example.rsschooltask4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.widget.EditText

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
            $KEY_ID INTEGER PRIMARY KEY,
            $KEY_FIRST_PARAMETER TEXT,
            $KEY_SECOND_PARAMETER INTEGER,
            $KEY_THIRD_PARAMETER TEXT);
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun createItem(firstParameter: EditText?, secondParameter: EditText?, thirdParameter: EditText?) {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put(KEY_FIRST_PARAMETER, firstParameter?.text.toString())
        values.put(KEY_SECOND_PARAMETER, secondParameter?.text.toString())
        values.put(KEY_THIRD_PARAMETER, thirdParameter?.text.toString())
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun readItem(): MutableList<ItemData> {
        val db: SQLiteDatabase = readableDatabase
        val list = mutableListOf<ItemData>()
        val selectAll = "SELECT * FROM $TABLE_NAME"
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
            return list
        }
    }

    fun updateItem(
        id: Long,
        firstParameter: EditText?,
        secondParameter: EditText?,
        thirdParameter: EditText?,
    ) {
        val db:SQLiteDatabase = writableDatabase
        val values = ContentValues()

        if (firstParameter?.text?.isNotEmpty()!!) values.put(KEY_FIRST_PARAMETER, firstParameter.text.toString())
        if (secondParameter?.text?.isNotEmpty()!!) values.put(KEY_SECOND_PARAMETER, secondParameter.text.toString())
        if (thirdParameter?.text?.isNotEmpty()!!) values.put(KEY_THIRD_PARAMETER, thirdParameter.text.toString())

        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                !values.isEmpty
            } else {
                !values.equals("")
            }
        ) db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(id.toString()))
    }

    fun deleteItem(id: Long) {
        val db:SQLiteDatabase = writableDatabase

        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
    }
}