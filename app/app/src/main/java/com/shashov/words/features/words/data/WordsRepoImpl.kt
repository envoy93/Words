package com.shashov.words.features.words.data

import com.shashov.words.app.AppDatabase
import com.shashov.words.features.words.data.local.Category
import com.shashov.words.features.words.data.local.Word
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordsRepoImpl @Inject
constructor(private val appDatabase: AppDatabase) : WordsRepo {
    override fun getCategory(id: Int): Flowable<Category> {
        return appDatabase.wordsDao().getCategory(id)
    }

    override fun getTopCategories(): Flowable<List<Category>> {
        return appDatabase.wordsDao().getTopCategories()
    }

    override fun getCategories(parentId: Int): Flowable<List<Category>> {
        return appDatabase.wordsDao().getCategories(parentId)
    }

    override fun getWords(categoryId: Int): Flowable<List<Word>> {
        return appDatabase.wordsDao().getWords(categoryId)
    }

    override fun getWord(): Flowable<Word> {
        return appDatabase.wordsDao().getWord()
    }

    companion object {
        private val TAG: String = WordsRepoImpl::class.java.simpleName!!
    }
}