package com.example.ywts22b1num7184.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "words.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "words"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FOREIGN_WORD = "foreign_word"
        private const val COLUMN_NATIVE_WORD = "native_word"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_FOREIGN_WORD TEXT NOT NULL,
                $COLUMN_NATIVE_WORD TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertWord(foreignWord: String, nativeWord: String): Boolean {
        deleteWord(foreignWord);
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FOREIGN_WORD, foreignWord)
            put(COLUMN_NATIVE_WORD, nativeWord)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun getAllWords(): List<Pair<String, String>> {
        val wordsList = mutableListOf<Pair<String, String>>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val foreignWord = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOREIGN_WORD))
                val nativeWord = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NATIVE_WORD))
                wordsList.add(Pair(foreignWord, nativeWord))
            } while (cursor.moveToNext())
        }
        cursor?.close()
        db.close()
        return wordsList
    }

    fun findWord(foreignWord: String): String? {
        var nativeWord: String? = null;
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE lower($COLUMN_FOREIGN_WORD) = lower('$foreignWord')", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                nativeWord = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NATIVE_WORD));
            } while (cursor.moveToNext())
        }
        cursor?.close()
        db.close()
        return nativeWord
    }

    fun deleteWord(foreignWord: String) {
        val db = readableDatabase
        val query = "DELETE FROM $TABLE_NAME WHERE lower($COLUMN_FOREIGN_WORD) = lower(?)"
        val statement = db.compileStatement(query)
        statement.bindString(1, foreignWord) // Bind the value safely
        statement.executeUpdateDelete()
        statement.close()
        db.close()
    }
}
