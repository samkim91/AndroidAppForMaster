package kr.co.soogong.master.ui.sign.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.domain.usecase.SetMasterKeyCodeUseCase
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val setMasterKeyCodeUseCase: SetMasterKeyCodeUseCase,
    private val authService: AuthService
) : BaseViewModel() {

    fun getUserInfo(email: String, password: String) {
        if (BuildConfig.DEBUG) {
            setMasterKeyCodeUseCase("919dcdf215133b52")
            successToSignIn()
            return
        }
        authService.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("getUserInfo: $it")
                setMasterKeyCodeUseCase(it)
                successToSignIn()
            }, {
                Timber.tag(TAG).w("getUserInfo: $it")
                failToSignIn()
            })
            .addToDisposable()
    }

    private val _event = MutableLiveData<Event<String>>()
    val event: LiveData<Event<String>>
        get() = _event

    private fun successToSignIn() {
        _event.postValue(Event(SUCCESS))
    }

    private fun failToSignIn() {
        _event.postValue(Event(FAIL))
    }

    companion object {
        private const val TAG = "SignInViewModel"
        const val SUCCESS = "SUCCESS"
        const val FAIL = "FAIL"
    }
}