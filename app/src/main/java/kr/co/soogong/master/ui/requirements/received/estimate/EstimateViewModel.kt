package kr.co.soogong.master.ui.requirements.received.estimate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.requirements.Estimate
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.requirements.progress.detail.estimate.ProgressEstimateViewModel
import kr.co.soogong.master.util.Event
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class EstimateViewModel @AssistedInject constructor(
    repository: Repository,
    private val httpClient: HttpClient,
    @Assisted private val keycode: String
) : BaseViewModel() {
    private val _requirement = repository.getRequirement(keycode)
    val requirement: LiveData<Requirement?>
        get() = _requirement

    private val _event = MutableLiveData<Event<String>>()
    val event: LiveData<Event<String>>
        get() = _event

    fun onClickedSend(estimate: Estimate) {
        httpClient.sendMessage(
            branchKeycode = InjectHelper.keyCode,
            keycode = keycode,
            estimate = estimate
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { message ->
                    Timber.tag(TAG).d("onClickedSend: $message")
                    _event.value = Event(SEND_EVENT)
                },
                {
                    Timber.tag(TAG).e("onClickedSend: $it")
                    _event.value = Event(SEND_EVENT)
                }
            )
            .addToDisposable()
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(keycode: String): ProgressEstimateViewModel
    }

    companion object {
        private const val TAG = "DetailViewModel"
        const val SEND_EVENT = "SEND_EVENT"

        fun provideFactory(
            assistedFactory: AssistedFactory,
            keycode: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(keycode) as T
            }
        }
    }
}