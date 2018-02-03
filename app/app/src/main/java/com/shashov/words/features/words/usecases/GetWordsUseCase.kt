package com.shashov.words.features.words.usecases

import android.util.Log
import com.shashov.words.base.usecases.UseCase
import com.shashov.words.features.words.data.WordsRepo
import com.shashov.words.features.words.data.local.Category
import com.shashov.words.features.words.data.local.Word
import com.shashov.words.features.words.presentation.SplashViewModel
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class GetWordsUseCase @Inject
internal constructor(private val repo: WordsRepo) : UseCase() {

    fun getCategory(id: Int): Category? {
        Log.d(TAG, "called subscribe on getTopCategories flowable")
        return repo.getCategory(id).blockingFirst()
    }

    fun getTopCategories(): List<Category>? {
        Log.d(TAG, "called subscribe on getTopCategories flowable")
        return repo.getTopCategories().blockingFirst()
    }

    fun getCategories(input: Input, subscriber: DisposableSubscriber<List<Category>>) {

        Flowable.just(input.id)
                .map<List<Category>> { id ->
                    var categories = repo.getCategories(id).blockingFirst()
                    for (category in categories) {
                        category.categories.addAll(repo.getCategories(category.id).blockingFirst())
                    }
                    categories
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(input.observerOnScheduler)
                .subscribe(subscriber)

        Log.d(TAG, "called subscribe on getCategories flowable")
        disposables.add(subscriber)
    }

    fun getWords(input: Input, subscriber: DisposableSubscriber<List<Word>>) {

        Flowable.just(input.id)
                .map<List<Word>> { id ->
                    repo.getWords(id).blockingFirst()
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(input.observerOnScheduler)
                .subscribe(subscriber)

        Log.d(TAG, "called subscribe on getWords flowable")
        disposables.add(subscriber)
    }

    fun getWord(input: Input, subscriber: SplashViewModel.SplashUseCaseSubscriber) {
        Flowable.just(input.id)
                //.delay(5, TimeUnit.SECONDS)
                .map<Word?> { title ->
                    repo.getWord().blockingFirst()
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(input.observerOnScheduler)
                .subscribe(subscriber)

        Log.d(TAG, "called subscribe on getWords flowable")
        disposables.add(subscriber)
    }

    class Input(val id: Int, val observerOnScheduler: Scheduler)

    companion object {
        private val TAG = GetWordsUseCase::class.java.simpleName
    }
}