package kr.co.soogong.master.ui.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kr.co.soogong.master.utility.Event

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

    protected val ioDispatchers = Dispatchers.IO + coroutineExceptionHandler
    protected val uiDispatchers = Dispatchers.Main + coroutineExceptionHandler

    private val compositeDisposable = CompositeDisposable()

    operator fun CompositeDisposable.plus(disposable: Disposable) {
        add(disposable)
    }

    protected fun Disposable.addToDisposable(): Disposable = apply { compositeDisposable.add(this) }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private val _action = MutableLiveData<Event<String>>()
    val action: LiveData<Event<String>>
        get() = _action

    private val _event = MutableLiveData<Event<Pair<String, Any?>>>()
    val event: LiveData<Event<Pair<String, Any?>>>
        get() = _event

    fun setAction(event: String) {
        _action.postValue(Event(event))
    }

    fun sendEvent(event: String, message: Any){
        _event.value = Event(event to message)
    }
}