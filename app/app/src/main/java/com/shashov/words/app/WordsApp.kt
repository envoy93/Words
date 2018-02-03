package com.shashov.words.app

import android.app.Application
import com.shashov.words.features.words.DaggerWordsComponent
import com.shashov.words.features.words.WordsComponent
import com.shashov.words.features.words.WordsModule

class WordsApp : Application() {
    lateinit var appComponent: AppComponent
    var wordsComponent: WordsComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = buildAppComponent()
    }

    private fun buildAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun buildWordsComponent(): WordsComponent {
        wordsComponent = DaggerWordsComponent.builder()
                .appComponent(appComponent)
                .wordsModule(WordsModule())
                .build()
        return wordsComponent!!
    }

    fun releaseDrugsComponent() {
        wordsComponent = null
    }
}