package com.ganesh.gituser.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main


    // to show progress view
    var canShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    // to store error message
    var errorMessage: MutableLiveData<String> = MutableLiveData()


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}