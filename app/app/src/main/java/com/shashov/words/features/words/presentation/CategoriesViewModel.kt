package com.shashov.words.features.words.presentation

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.shashov.words.app.WordsApp
import com.shashov.words.features.words.data.local.Category
import com.shashov.words.features.words.usecases.GetWordsUseCase
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {

    private var topCategories: MutableLiveData<ArrayList<Category>>
    private var categories: MutableLiveData<ArrayList<Category>>
    private var topCategory: Category?

    private var isLoading: MutableLiveData<Boolean>

    @Inject
    lateinit var getWordsUseCase: Lazy<GetWordsUseCase>

    init {
        (application as WordsApp).wordsComponent!!.inject(this)

        categories = MutableLiveData()
        topCategories = MutableLiveData()
        isLoading = MutableLiveData()

        categories.value = ArrayList()
        isLoading.value = false

        topCategory = getWordsUseCase.get().getCategory(0) //TODO
        var list = ArrayList<Category>()
        list.addAll(getWordsUseCase.get().getTopCategories()!!)
        topCategories.value = list
    }

    internal fun getCategories(): LiveData<ArrayList<Category>> {
        return categories;
    }

    internal fun getTopCategories(): LiveData<ArrayList<Category>> {
        return topCategories;
    }

    internal fun loadCategories(category: Category) {
        isLoading.value = true
        topCategory = category
        getWordsUseCase.get().getCategories(
                GetWordsUseCase.Input(category.id, AndroidSchedulers.mainThread()),
                CategoriesUseCaseSubscriber())
    }

    internal fun getLoading(): LiveData<Boolean> {
        return isLoading
    }

    override fun onCleared() {
        super.onCleared()
        // remove subscriptions if any
        getWordsUseCase.get().cancel()
        Log.d(CategoriesViewModel.TAG, "onCleared")
    }

    inner class TopCategoriesUseCaseSubscriber : DisposableSubscriber<List<Category>>() {

        override fun onNext(drugs: List<Category>) {

            Log.d(TAG, "Received response for search items")
            this@CategoriesViewModel.topCategories.value!!.clear();
            this@CategoriesViewModel.topCategories.value!!.addAll(drugs)
            this@CategoriesViewModel.topCategories.postValue(this@CategoriesViewModel.topCategories.value)
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "Received error: " + e.toString())
            this@CategoriesViewModel.topCategories.value = ArrayList()
        }

        override fun onComplete() {
            Log.d(TAG, "onComplete called")
        }

    }

    inner class CategoriesUseCaseSubscriber : DisposableSubscriber<List<Category>>() {

        override fun onNext(drugs: List<Category>) {

            Log.d(TAG, "Received response for search items")
            this@CategoriesViewModel.categories.value!!.clear();
            this@CategoriesViewModel.categories.value!!.addAll(drugs)
            this@CategoriesViewModel.categories.postValue(this@CategoriesViewModel.categories.value)
            isLoading.value = false
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "Received error: " + e.toString())
            this@CategoriesViewModel.categories.value = ArrayList()
            isLoading.value = false
        }

        override fun onComplete() {
            Log.d(TAG, "onComplete called")
        }

    }

    companion object {
        private val TAG = CategoriesViewModel::class.java.simpleName

    }

    fun getTopCategoryPosition(): Int {
        if (topCategory == null) {
            return 0;
        }

        var i = 0
        for (c in topCategories.value!!.iterator()) {
            if (c.id.equals(topCategory!!.id)) {
                return i
            }
            i++
        }
        return 0
    }
}