package com.example.retrofittest.ui

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofittest.repository.DogsApiRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val dogsApiRepository: DogsApiRepository) : ViewModel() {

    var disposable: Disposable? = null
    val liveData = MutableLiveData<String>()

    fun getUrl(progress : ProgressBarrCallback) {
        disposable = dogsApiRepository.getDogImageUrl()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress.showProgress(true) }
            .doOnSuccess { progress.showProgress(false) }
            .map { it.message }
            .subscribe({
                liveData.value = it
            }, {
                liveData.value = null
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

    interface ImageCallBack {
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

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}