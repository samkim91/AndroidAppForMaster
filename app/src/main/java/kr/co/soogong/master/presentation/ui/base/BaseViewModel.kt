package kr.co.soogong.master.presentation.ui.base

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

    // NOTE: 2022/02/16 event 로 감싸진 liveData 들은 프래그먼트 간 데이터 이동을 사용 했을 때,
    // observe 가 최초 실행된 곳에서만 작동하는 문제가 있다. 왜 그런지는 아직 찾지 못 했음.
    // 프래그먼트 간 데이터 이동 : (by viewModels({ requireParentFragment() })) 를 말함.
    private val _action = MutableLiveData<Event<String>>()
    val action: LiveData<Event<String>>
        get() = _action

    private val _event = MutableLiveData<Event<Pair<String, Any?>>>()
    val event: LiveData<Event<Pair<String, Any?>>>
        get() = _event

    private val _message = MutableLiveData<Pair<String, Any>>()
    val message: LiveData<Pair<String, Any>>
        get() = _message

    fun setAction(event: String) {
        _action.postValue(Event(event))
    }

    fun sendEvent(event: String, message: Any) {
        _event.value = Event(event to message)
    }

    fun sendMessage(key: String, value: Any) {
        _message.value = (key to value)
    }

    companion object {
        const val SHOW_LOADING = "SHOW_LOADING"
        const val DISMISS_LOADING = "DISMISS_LOADING"

        const val REQUEST_SUCCESS = "REQUEST_SUCCESS"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}