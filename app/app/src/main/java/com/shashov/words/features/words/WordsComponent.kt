package com.shashov.words.features.words

import com.shashov.words.app.AppComponent
import com.shashov.words.features.words.presentation.CategoriesViewModel
import com.shashov.words.features.words.presentation.SplashViewModel
import com.shashov.words.features.words.presentation.WordsViewModel
import dagger.Component

@WordsScope
@Component(modules = arrayOf(WordsModule::class), dependencies = arrayOf(AppComponent::class))
interface WordsComponent {
    fun inject(target: WordsViewModel)
    fun inject(target: CategoriesViewModel)
    fun inject(target: SplashViewModel)
}