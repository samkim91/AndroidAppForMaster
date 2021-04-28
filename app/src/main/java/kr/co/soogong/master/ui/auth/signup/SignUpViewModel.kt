package kr.co.soogong.master.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.domain.usecase.CheckIdExistUseCase
import kr.co.soogong.master.domain.usecase.DoLoginUseCase
import kr.co.soogong.master.domain.usecase.DoSignUpUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.utils.ListLiveData
import kr.co.soogong.master.util.Event
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val doSignUpUseCase: DoSignUpUseCase,
    private val doLoginUseCase: DoLoginUseCase,
    private val checkIdExistUseCase: CheckIdExistUseCase
) : BaseViewModel() {

    var indicator = MutableLiveData<Int>()

    // Step 1
    var phoneNumber = MutableLiveData<String>()

    // Step 2
    var certificationCode = MutableLiveData<Int>()

    // Step 2 sub
    var signInPassword = MutableLiveData<String>()

    // Step 3
    var signUpPassword = MutableLiveData<String>()
    var signUpConfirmPassword = MutableLiveData<String>()

    // Step 4
    var businessRepresentativeName = MutableLiveData<String>()

    // Step 5
    var businessType = ListLiveData<BusinessType>()



    fun checkIdExist(){
        Timber.tag(TAG).d("checkIsIdExistent: ")
        checkIdExistUseCase(phoneNumber.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("onSuccess: $it")
                    setAction(ID_IS_EXISTENT)
                },
                onError = {
                    Timber.tag(TAG).d("onError: $it")
                    setAction(ID_NOT_EXISTENT)
                }
            ).addToDisposable()

    }

    fun requestCertificationCode(){
        Timber.tag(TAG).d("requestCertificationCode: ")

        // Todo.. 이미 있는 계정인지 확인
        // 있으면, 로그인으로 안내
        // 없으면, 인증코드 메시지 보내고 입력 액티비티로 안내

    }

    fun requestConfirmCertificationCode(){
        // 입력한 인증번호를 확인
        // 인증되면, 다음화면으로
        // 안 되면, alert 표시
    }

    fun requestLogin(){
        Timber.tag(TAG).d("requestLogin: ")

        doLoginUseCase(phoneNumber.value, signInPassword.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("onSuccess: $it")
                    setAction(SIGN_IN_SUCCESSFUL)
                },
                onError = {
                    Timber.tag(TAG).d("onError: $it")
                    setAction(SIGN_IN_FAILED)
                }
            ).addToDisposable()
    }

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
        const val SIGN_UP_SUCCESS = "SIGN_UP_SUCCESS"
        const val SIGN_IN_SUCCESSFUL = "SIGN_IN_SUCCESSFUL"
        const val SIGN_IN_FAILED = "SIGN_IN_FAILED"

        const val ID_IS_EXISTENT = "ID_IS_EXISTENT"
        const val ID_NOT_EXISTENT = "ID_NOT_EXISTENT"


    }
}