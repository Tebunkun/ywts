package com.example.ywts22b1num7184.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val foreignWord: String,
    val nativeWord: String
)

