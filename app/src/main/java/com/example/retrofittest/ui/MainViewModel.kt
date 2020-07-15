package com.example.retrofittest.ui

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.example.retrofittest.repository.DogsApiRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val dogsApiRepository: DogsApiRepository) : ViewModel() {

    var disposable: Disposable? = null

    fun getUrl(progress : ProgressBarrCallback,callBack: ImageCallBack) {
        disposable = dogsApiRepository.getDogImageUrl()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.message }
            .doOnSubscribe { progress.showProgress(true) }
            .doOnSuccess { progress.showProgress(false) }
            .subscribe({
                callBack.onComplete(it)
            }, {
                callBack.onError()
            })
    }

    fun isConnect(context: Context, connectionCallBack: ConnectionCallBack) {
        disposable = hasInternetConnection(context)
            .subscribe({
                connectionCallBack.isConnect(it)
            }, {
                connectionCallBack.onError(it.localizedMessage)
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    interface ImageCallBack {
        fun onComplete(url: String)
        fun onError()
    }

    interface ConnectionCallBack {
        fun isConnect(status: Boolean)
        fun onError(error: String)
    }

    interface ProgressBarrCallback {
        fun showProgress(boolean: Boolean)
    }

    private fun hasInternetConnection(context: Context): Single<Boolean> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        return Single.fromCallable {
            isConnected
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}