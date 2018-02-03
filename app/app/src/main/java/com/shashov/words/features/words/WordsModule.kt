package com.shashov.words.features.words

import com.shashov.words.app.AppDatabase
import com.shashov.words.features.words.data.WordsRepo
import com.shashov.words.features.words.data.WordsRepoImpl
import com.shashov.words.features.words.data.local.WordsDao
import com.shashov.words.features.words.data.remote.WikiApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class WordsModule {

    @WordsScope
    @Provides
    internal fun provideDrugsDao(db: AppDatabase): WordsDao {
        return db.wordsDao()
    }

    @WordsScope
    @Provides
    internal fun provideWikiApiService(): WikiApiService {
        return Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WikiApiService::class.java)
    }

    @WordsScope
    @Provides
    internal fun provideDrugsRepo(appDatabase: AppDatabase, apiService: WikiApiService): WordsRepo {
        return WordsRepoImpl(appDatabase)
    }
}