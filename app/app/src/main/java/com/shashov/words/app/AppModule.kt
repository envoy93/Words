package com.shashov.words.app

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val application: Application) {

    @Singleton
    @Provides
    fun provideApp(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideDb(): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "words.db")
                .openHelperFactory(com.shashov.words.sqlasset.AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .build()
    }
}