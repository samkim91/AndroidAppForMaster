package kr.co.soogong.master.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.data.user.SignUpModel
import kr.co.soogong.master.data.user.Step1Model
import kr.co.soogong.master.domain.usecase.DoSignUpUseCase
import kr.co.soogong.master.network.RxException
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val doSignUpUseCase: DoSignUpUseCase
) : BaseViewModel() {

    var companyName = MutableLiveData<String>()
    var briefIntroduction = MutableLiveData<String>()
    var businessType = MutableLiveData<String>()
    var businessRegistrationNumber = MutableLiveData<String>()
    var businessRegistrationCertificate = MutableLiveData<String>()
    var birthday = MutableLiveData<String>()
    var businessRepresentative = MutableLiveData<String>()
    var phoneNumber = MutableLiveData<String>()
    var workExperience = MutableLiveData<String>()



    fun signUp(signUpInfo: SignUpInfo) {

    }

    private val _event = MutableLiveData<Event<Pair<String, Any?>>>()
    val event: LiveData<Event<Pair<String, Any?>>>
        get() = _event

    private fun sendEvent(event: String, message: Any?) {
        _event.postValue(Event(event to message))
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