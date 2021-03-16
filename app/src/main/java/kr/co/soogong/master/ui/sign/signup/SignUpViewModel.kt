package kr.co.soogong.master.ui.sign.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import kr.co.soogong.master.util.http.HttpClient
import kr.co.soogong.master.util.http.RxException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val httpClient: HttpClient
) : BaseViewModel() {
    var area: String? = null
    var location: String? = null

    private val _event = MutableLiveData<Event<Pair<String, Any?>>>()
    val event: LiveData<Event<Pair<String, Any?>>>
        get() = _event

    private fun sendEvent(event: String, message: Any?) {
        _event.postValue(Event(event to message))
    }

    fun signUp(signUpInfo: SignUpInfo) {
        httpClient.actionSignUp(signUpInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("signUp: $it")
                sendEvent(SIGNUP_SUCCESS, null)
            }, {
                Timber.tag(TAG).w("signUp: $it")
                if (it is RxException) {
                    val message = it.data

                    if (message?.has("email") == true) {
                        sendEvent(EMAIL_ERROR, message = message.get("email").asString)
                    }
                    if (message?.has("password") == true) {
                        sendEvent(PASSWORD_ERROR, message = message.get("password").asString)
                    }
                    if (message?.has("password_confirmation") == true) {
                        sendEvent(
                            PASSWORD_CONFIRMATION_ERROR,
                            message = message.get("password_confirmation").asString
                        )
                    }
                    if (message?.has("username") == true) {
                        sendEvent(USER_NAME_ERROR, message = message.get("username").asString)
                    }
                }
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "SignUpViewModel"
        const val SIGNUP_SUCCESS = "SIGNUP_SUCCESS"
        const val EMAIL_ERROR = "EMAIL_ERROR"
        const val PASSWORD_ERROR = "PASSWORD_ERROR"
        const val PASSWORD_CONFIRMATION_ERROR = "PASSWORD_CONFIRMATION_ERROR"
        const val USER_NAME_ERROR = "USER_NAME_ERROR"
    }
}