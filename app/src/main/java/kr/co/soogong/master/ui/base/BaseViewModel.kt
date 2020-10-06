package kr.co.soogong.master.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kr.co.soogong.master.util.Event

abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    operator fun CompositeDisposable.plus(disposable: Disposable) {
        add(disposable)
    }

    protected fun Disposable.addToDisposable(): Disposable = apply { compositeDisposable.add(this) }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private val _completeEvent = MutableLiveData<Event<Unit>>()
    val completeEvent: LiveData<Event<Unit>>
        get() = _completeEvent

    fun complete() {
        _completeEvent.value = Event(Unit)
    }
}