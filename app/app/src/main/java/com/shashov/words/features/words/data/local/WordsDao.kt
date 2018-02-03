package com.shashov.words.features.words.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface WordsDao {

    //@get:Query("SELECT * FROM words LIMIT 500")
    //val all: Flowable<List<Drugs>>

    @Query("SELECT * FROM categories WHERE id = :arg0 order By id LIMIT 1")
    fun getCategory(id: Int): Flowable<Category>

    @Query("SELECT * FROM categories WHERE lvl = 0 Order By position")
    fun getTopCategories(): Flowable<List<Category>>

    @Query("SELECT * FROM categories WHERE parent_id = :arg0 Order By position")
    fun getCategories(parentId: Int): Flowable<List<Category>>

    @Query("SELECT * FROM categories WHERE (parent_id = :arg0) and (lvl = :arg1) Order By position")
    fun getCategories(parentId: Int, lvl: Int): Flowable<List<Category>>

    @Query("SELECT * FROM words WHERE category_id = :arg0 ORDER BY position")
    fun getWords(categoryId: Int): Flowable<List<Word>>

    @Query("SELECT * FROM words LIMIT 1")
    fun getWord(): Flowable<Word>
}