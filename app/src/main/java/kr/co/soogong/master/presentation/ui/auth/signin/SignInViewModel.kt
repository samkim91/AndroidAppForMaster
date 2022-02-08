package kr.co.soogong.master.presentation.ui.auth.signin

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
import kr.co.soogong.master.domain.usecase.auth.SignInUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val checkUserExistentUseCase: CheckUserExistentUseCase,
    private val signInUseCase: SignInUseCase,
) : BaseViewModel() {
    val tel = MutableLiveData("")
    val certificationCode = MutableLiveData("")
    val auth = MutableLiveData(Firebase.auth)
    val phoneAuthCredential = MutableLiveData<PhoneAuthCredential>()
    val storedVerificationId = MutableLiveData("")
    val resendToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()
    val uid = MutableLiveData("")

    fun checkUserExist() {
        Timber.tag(TAG).d("checkUserExist: ")

        tel.value?.let { tel ->
            checkUserExistentUseCase(tel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("checkUserExist onSuccess: $it")
                        if (it) setAction(PHONE_NUMBER_EXIST) else setAction(PHONE_NUMBER_NOT_EXIST)
                    },
                    onError = {
                        Timber.tag(TAG).d("checkUserExist onError: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun requestSignIn() {
        Timber.tag(TAG).d("requestSignIn: ${uid.value}, ${tel.value}")

        uid.value?.let { uid ->
            signInUseCase(tel.value!!, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("requestSignIn successToSignIn: $it")
                        setAction(SIGN_IN_SUCCESSFULLY)
                    },
                    onError = {
                        Timber.tag(TAG).d("requestSignIn failToSignIn: $it ")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "SignInViewModel"
        const val PHONE_NUMBER_EXIST = "PHONE_NUMBER_EXIST"
        const val PHONE_NUMBER_NOT_EXIST = "PHONE_NUMBER_NOT_EXIST"
        const val SIGN_IN_SUCCESSFULLY = "SIGN_IN_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}