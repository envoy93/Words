package com.shashov.words.base.usecases

import io.reactivex.disposables.CompositeDisposable

abstract class UseCase {
    protected var disposables = CompositeDisposable()

    fun cancel() {
        disposables.clear()
    }
}