package kr.co.soogong.master.ui.auth.signup

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.domain.usecase.DoSignUpUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.utils.ListLiveData
import kr.co.soogong.master.util.Event
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val doSignUpUseCase: DoSignUpUseCase
) : BaseViewModel() {

    var companyName = MutableLiveData<String>()
    var briefIntroduction = MutableLiveData<String>()
    var businessType = MutableLiveData<String>()
    var businessRegistrationNumber = MutableLiveData<String>()
    var businessRegistrationCertificate = ListLiveData<Uri>()
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