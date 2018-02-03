package com.shashov.words.features.words.presentation

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.shashov.words.app.WordsApp
import com.shashov.words.features.words.data.local.Word
import com.shashov.words.features.words.usecases.GetWordsUseCase
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private var isLoading: MutableLiveData<Boolean>
    @Inject
    lateinit var getWordsUseCase: Lazy<GetWordsUseCase>

    init {
        (application as WordsApp).wordsComponent!!.inject(this)

        isLoading = MutableLiveData()
        isLoading.value = true
    }

    internal fun getLoading(): LiveData<Boolean> {
        return isLoading
    }

    internal fun load() {
        isLoading.value = true
        getWordsUseCase.get().getWord(
                GetWordsUseCase.Input(0, AndroidSchedulers.mainThread()),
                SplashUseCaseSubscriber())
    }

    override fun onCleared() {
        super.onCleared()
        // remove subscriptions if any
        getWordsUseCase.get().cancel()
        Log.d(TAG, "onCleared")
    }

    inner class SplashUseCaseSubscriber : DisposableSubscriber<Word?>() {

        override fun onNext(list: Word?) {
            Log.d(TAG, "Received response for words")
            isLoading.value = false
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "Received error: " + e.toString())
            isLoading.value = false
        }

        override fun onComplete() {
            Log.d(TAG, "onComplete called")
        }

    }

    companion object {
        private val TAG = SplashViewModel::class.java.simpleName
    }
}