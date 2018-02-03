package com.shashov.words.app

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.shashov.words.features.words.data.local.Category
import com.shashov.words.features.words.data.local.Word
import com.shashov.words.features.words.data.local.WordsDao

@Database(entities = arrayOf(Word::class, Category::class), version = 8)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}