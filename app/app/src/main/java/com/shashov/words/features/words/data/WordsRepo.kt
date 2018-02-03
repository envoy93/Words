package com.shashov.words.features.words.data

import com.shashov.words.features.words.data.local.Category
import com.shashov.words.features.words.data.local.Word
import io.reactivex.Flowable

interface WordsRepo {
    fun getTopCategories(): Flowable<List<Category>>
    fun getCategories(parentId: Int): Flowable<List<Category>>
    fun getWords(categoryId: Int): Flowable<List<Word>>
    fun getWord(): Flowable<Word>
    fun getCategory(id: Int): Flowable<Category>
}
