package com.shashov.words.features.words.presentation

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.shashov.words.app.WordsApp
import com.shashov.words.features.words.data.local.Category
import com.shashov.words.features.words.data.local.Word
import com.shashov.words.features.words.usecases.GetWordsUseCase
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class WordsViewModel(application: Application) : AndroidViewModel(application) {

    private var words: MutableLiveData<List<Word>>
    private var category: MutableLiveData<Category>
    private var isLoading: MutableLiveData<Boolean>

    @Inject
    lateinit var getWordsUseCase: Lazy<GetWordsUseCase>

    init {
        (application as WordsApp).wordsComponent!!.inject(this)
        words = MutableLiveData()
        category = MutableLiveData()
        isLoading = MutableLiveData()

        words.value = ArrayList()
        category.value = null
        isLoading.value = false
    }

    internal fun getAnalogs(): LiveData<List<Word>> {
        return words;
    }

    internal fun getCategory(): LiveData<Category> {
        return category
    }

    internal fun getLoading(): LiveData<Boolean> {
        return isLoading
    }

    internal fun loadAnalogs(c: Category) {
        isLoading.value = true
        category.value = c
        getWordsUseCase.get().getWords(
                GetWordsUseCase.Input(c.id, AndroidSchedulers.mainThread()),
                SearchUseCaseSubscriber())
    }

    override fun onCleared() {
        super.onCleared()
        // remove subscriptions if any
        getWordsUseCase.get().cancel()
        Log.d(TAG, "onCleared")
    }

    inner class SearchUseCaseSubscriber : DisposableSubscriber<List<Word>>() {

        override fun onNext(list: List<Word>) {
            Log.d(TAG, "Received response for words")
            with(words) {
                isLoading.value = false
                with(value!! as ArrayList<Word>) {
                    clear()
                    addAll(list)
                }
                postValue(this.value!!)
            }
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
        private val TAG = WordsViewModel::class.java.simpleName
    }
}
