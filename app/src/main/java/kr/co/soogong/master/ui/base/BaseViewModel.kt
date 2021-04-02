package kr.co.soogong.master.ui.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kr.co.soogong.master.util.Event

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    protected val coroutineExceptionHanlder =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }

    protected val ioDispatchers = Dispatchers.IO + coroutineExceptionHanlder
    protected val uiDispatchers = Dispatchers.Main + coroutineExceptionHanlder

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

    fun setAction(event: String) {
        _action.postValue(Event(event))
    }
}