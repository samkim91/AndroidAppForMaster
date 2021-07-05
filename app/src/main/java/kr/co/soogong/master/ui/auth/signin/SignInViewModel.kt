package kr.co.soogong.master.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.CheckUserExistentUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveMasterUidInSharedUseCase
import kr.co.soogong.master.domain.usecase.auth.SignInUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.PhoneNumberHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val checkUserExistentUseCase: CheckUserExistentUseCase,
    private val signInUseCase: SignInUseCase
) : BaseViewModel() {
    val tel = MutableLiveData("")
    val certificationCode = MutableLiveData("")
    val auth = MutableLiveData(Firebase.auth)
    val phoneAuthCredential = MutableLiveData<PhoneAuthCredential>()
    val storedVerificationId = MutableLiveData("")
    val resendToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()
    val uid = MutableLiveData("")

    private val _isEnabled = MutableLiveData(false)
    val isEnabled: LiveData<Boolean>
        get() = _isEnabled

    fun changeEnabled() {
        _isEnabled.value = !_isEnabled.value!!
    }

    fun checkUserExist() {
        Timber.tag(TAG).d("checkUserExist: ")

        tel.value?.let { tel ->
            checkUserExistentUseCase(PhoneNumberHelper.toGlobalNumber(tel))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("onSuccess: $it")
                        if (it) setAction(PHONE_NUMBER_EXIST) else setAction(
                            PHONE_NUMBER_NOT_EXIST
                        )
                    },
                    onError = {
                        Timber.tag(TAG).d("onError: $it")
                        setAction(CHECK_PHONE_NUMBER_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun requestSignIn() {
        Timber.tag(TAG).d("requestSignIn: ")

        uid.value?.let { uid ->
            signInUseCase(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("successToSignIn: $it")
                        setAction(SIGN_IN_SUCCESSFULLY)
                    },
                    onError = {
                        Timber.tag(TAG).d("failToSignIn: $it ")
                        setAction(SIGN_IN_FAILED)
                    }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "SignInViewModel"
        const val PHONE_NUMBER_EXIST = "PHONE_NUMBER_EXIST"
        const val PHONE_NUMBER_NOT_EXIST = "PHONE_NUMBER_NOT_EXIST"
        const val CHECK_PHONE_NUMBER_FAILED = "CHECK_PHONE_NUMBER_FAILED"
        const val SIGN_IN_SUCCESSFULLY = "SIGN_IN_SUCCESSFULLY"
        const val SIGN_IN_FAILED = "SIGN_IN_FAILED"
    }
}