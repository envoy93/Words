package com.shashov.words.features.words.data.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "categories")
class Category {
    @PrimaryKey
    var id: Int = 0
    @ColumnInfo(name = "parent_id")
    var parentId: Int = 0
    var title: String = "--"
    var lvl: Int = 0
    var position: Int = 0
    @Ignore
    var categories: ArrayList<Category> = ArrayList()
}

@Entity(tableName = "words")
class Word {
    @PrimaryKey
    var id: Int = 0
    @ColumnInfo(name = "category_id")
    var categoryId: Int = 0
    var position: Int = 0
    @ColumnInfo(name = "base")
    var base: Int = 0

    var title: String = "--"
    var translate: String = "--"
    var transcription: String = "--"
    var example: String = "--"
}