package kr.co.soogong.master.presentation.ui.auth

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.CheckUserExistentUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.utility.PhoneNumberHelper
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val checkUserExistentUseCase: CheckUserExistentUseCase,
) : BaseViewModel() {

    private val _auth: FirebaseAuth = Firebase.auth

    val tel = MutableLiveData("")
    val certificationCode = MutableLiveData("")

    private val _storedVerificationId = MutableLiveData("")
    private val _resendToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()

    private val _callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks by lazy {
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                Timber.tag(TAG).d("onCodeSent: ")

                _storedVerificationId.value = p0
                _resendToken.value = p1
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Timber.tag(TAG).d("onVerificationCompleted: ")

                signInWithPhoneAuthCredential(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Timber.tag(TAG).d("onVerificationFailed: ")

                when (p0) {
                    is FirebaseAuthInvalidCredentialsException -> setAction(INVALID_CREDENTIAL)
                    is FirebaseTooManyRequestsException -> setAction(TOO_MANY_REQUEST)
                    else -> setAction(REQUEST_FAILED)
                }
            }
        }
    }

    fun checkUserExist() {
        Timber.tag(TAG).d("checkUserExist: ")

        if (tel.value.isNullOrEmpty()) {
            setAction(REQUIRED_TEL)
            return
        }

        checkUserExistentUseCase(tel.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it) setAction(EXIST_USER) else setAction(NOT_EXIST_USER)
                },
                onError = {
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun startVerifyingPhoneNumber(activity: Activity) {
        Timber.tag(TAG).d("startVerifyingPhoneNumber: ")

        if (tel.value.isNullOrEmpty()) setAction(REQUIRED_TEL)

        PhoneAuthProvider.verifyPhoneNumber(
            PhoneAuthOptions.newBuilder(_auth)
                .setPhoneNumber(PhoneNumberHelper.toGlobalNumber(tel.value!!))
                .setTimeout(LIMIT_TIME_TO_AUTH, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(_callbacks)
                .apply {
                    if (_resendToken.value != null) this.setForceResendingToken(_resendToken.value!!)
                }
                .build()
        )

        setAction(if (_resendToken.value == null) CREDENTIAL_CODE_REQUESTED else CREDENTIAL_CODE_REQUESTED_AGAIN)
    }

    fun makePhoneAuthCredential() {
        Timber.tag(TAG).d("makePhoneAuthCredential: ")

        if (_storedVerificationId.value.isNullOrEmpty() || certificationCode.value.isNullOrEmpty()) {
            setAction(TRY_AGAIN)
            return
        }

        PhoneAuthProvider.getCredential(_storedVerificationId.value!!, certificationCode.value!!)
            .run { signInWithPhoneAuthCredential(this) }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Timber.tag(TAG).d("signInWithPhoneAuthCredential: ")

        _auth.signInWithCredential(credential).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> {
                    Timber.tag(TAG).d("isSuccessful: ")
                    sendEvent(TASK_SUCCESSFUL, task.result?.user?.uid!!)
                }
                task.isCanceled -> {
                    Timber.tag(TAG).d("isCanceled: ")
                    sendEvent(TASK_FAILED, task.exception?.message!!)
                }
            }
        }
    }

    companion object {

        private const val TAG = "AuthViewModel"
        private const val LIMIT_TIME_TO_AUTH = 120L

        const val REQUIRED_TEL = "REQUIRED_TEL"

        const val EXIST_USER = "EXIST_USER"
        const val NOT_EXIST_USER = "NOT_EXIST_USER"

        const val CREDENTIAL_CODE_REQUESTED = "CREDENTIAL_CODE_REQUESTED"
        const val CREDENTIAL_CODE_REQUESTED_AGAIN = "CREDENTIAL_CODE_REQUESTED_AGAIN"

        const val INVALID_CREDENTIAL = "INVALID_CREDENTIAL"
        const val TOO_MANY_REQUEST = "TOO_MANY_REQUEST"
        const val TRY_AGAIN = "TRY_AGAIN"

        const val TASK_SUCCESSFUL = "TASK_SUCCESSFUL"
        const val TASK_FAILED = "TASK_FAILED"
    }
}