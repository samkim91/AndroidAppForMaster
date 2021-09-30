@file:JvmName("LiveDataExt")

package kr.co.soogong.master.utility.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 참고 : https://gist.github.com/mirmilad/f7feb8007d6b572150cb84fef0b65879
fun <T> LiveData<T>.debounce(duration: Long = 1000L, coroutineScope: CoroutineScope) = MediatorLiveData<T>().also { mediator ->
    val source = this
    var job: Job? = null

    mediator.addSource(source) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(duration)
            mediator.value = source.value
        }
    }
}