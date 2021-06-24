package kr.co.soogong.master.ui.profile.detail.email

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.profile.GetEmailUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveEmailUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditEmailViewModel @Inject constructor(
    private val getEmailUseCase: GetEmailUseCase,
    private val saveEmailUseCase: SaveEmailUseCase,
) : BaseViewModel() {
    val localPart = MutableLiveData("")
    val domain = MutableLiveData("")

    fun requestEmail() {
        Timber.tag(TAG).d("requestEmail: ")
        getEmailUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it.contains("@")) {
                        localPart.postValue(it.split("@")[0])
                        domain.postValue(it.split("@")[1])
                    } else {
                        localPart.postValue(it)
                    }
                },
                onError = { setAction(GET_EMAIL_FAILED) }
            ).addToDisposable()
    }

    fun saveEmailInfo() {
        Timber.tag(TAG).d("saveEmailInfo: ")
        localPart.value?.let { localPart ->
            domain.value?.let { domain ->
                saveEmailUseCase(
                    email = "$localPart@$domain"
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onSuccess = { setAction(SAVE_EMAIL_SUCCESSFULLY) },
                        onError = { setAction(SAVE_EMAIL_FAILED) }
                    ).addToDisposable()
            }
        }
    }

    companion object {
        private const val TAG = "EditEmailViewModel"
        const val SAVE_EMAIL_SUCCESSFULLY = "SAVE_EMAIL_SUCCESSFULLY"
        const val SAVE_EMAIL_FAILED = "SAVE_EMAIL_FAILED"
        const val GET_EMAIL_FAILED = "GET_EMAIL_FAILED"
    }
}