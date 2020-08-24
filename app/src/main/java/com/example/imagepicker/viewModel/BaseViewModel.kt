package com.example.imagepicker.viewModel

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import com.example.imagepicker.util.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val isLoadingProgress = SingleLiveEvent<Boolean>()
    val toast = SingleLiveEvent<String>()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    @NonNull
    protected fun <T> asyncSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    @NonNull
    protected fun <T> loadingSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .doOnSubscribe { isLoadingProgress.value = true }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { isLoadingProgress.value = false }
        }
    }
}