package kr.co.soogong.master.ui.auth.signup

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.auth.SignInDto
import kr.co.soogong.master.data.model.major.BusinessType
import kr.co.soogong.master.domain.usecase.auth.*
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import kr.co.soogong.master.utility.PhoneNumberHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInTestUseCase: SignInTestUseCase,
    private val checkUserExistentUseCase: CheckUserExistentUseCase,
    private val requestVerificationCodeUseCase: RequestVerificationCodeUseCase,
    private val getPhoneAuthCredentialUseCase: GetPhoneAuthCredentialUseCase,
    private val resendVerificationCodeUseCase: ResendVerificationCodeUseCase,
) : BaseViewModel() {

    val indicator = MutableLiveData(0)

    // Step 1
    val tel = MutableLiveData("")

    // Step 2
    val certificationCode = MutableLiveData("")

    // Firebase Auth
    val auth = MutableLiveData(Firebase.auth)
    val phoneAuthCredential = MutableLiveData<PhoneAuthCredential>()
    val storedVerificationId = MutableLiveData("")
    val resendToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()
    val uid = MutableLiveData("")

    // Step 2 sub
    val signInPassword = MutableLiveData("")

    // Step 3
    val signUpPassword = MutableLiveData("")
    val signUpConfirmPassword = MutableLiveData("")

    // Step 4
    val ownerName = MutableLiveData("")

    // Step 5
    val businessTypes = ListLiveData<BusinessType>()

    // Step 6
    val roadAddress = MutableLiveData("")
    val detailAddress = MutableLiveData("")
    val latitude = MutableLiveData(0.0)
    val longitude = MutableLiveData(0.0)

    // Step 7
    val serviceArea = MutableLiveData("")
    val serviceAreaToInt = MutableLiveData(0)

    // Step 8
    val agreedPrivacyPolicy = MutableLiveData(false)
    val marketingPush = MutableLiveData(false)
    val appPush = MutableLiveData(false)


    fun checkUserExist() {
        Timber.tag(TAG).d("checkUserExist: ")

        tel.value?.let { tel ->
            checkUserExistentUseCase(PhoneNumberHelper.toGlobalNumber(tel))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("onSuccess: $it")
                        if (it) setAction(PHONE_NUMBER_EXIST) else setAction(PHONE_NUMBER_NOT_EXIST)
                    },
                    onError = {
                        Timber.tag(TAG).d("onError: $it")
                        setAction(CHECK_PHONE_NUMBER_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun requestVerificationCode(
        callbackActivity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
    ) {
        Timber.tag(TAG).d("requestVerificationCode: ")
        tel.value?.let {
            requestVerificationCodeUseCase(
                callbackActivity = callbackActivity,
                callbacks = callbacks,
                firebaseAuth = auth.value!!,
                phoneNumber = tel.value!!,
            )
        }
    }

    fun getPhoneAuthCredential() {
        Timber.tag(TAG)
            .d("getPhoneAuthCredential: ${storedVerificationId.value}, ${certificationCode.value}")
        storedVerificationId.value?.let { verificationId ->
            certificationCode.value?.let { code ->
                phoneAuthCredential.value =
                    getPhoneAuthCredentialUseCase(verificationId = verificationId, code = code)
                Timber.tag(TAG).d("getPhoneAuthCredential: ${phoneAuthCredential.value}")
                signInWithPhoneAuthCredential()
            }
        }
    }

    private fun signInWithPhoneAuthCredential() {
        Timber.tag(TAG)
            .d("signInWithPhoneAuthCredential: ${phoneAuthCredential.value} / ${auth.value}")

        phoneAuthCredential.value?.let { credential ->
            auth.value?.signInWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.tag(TAG).d("signInWithPhoneAuthCredential successfully: ")
                    uid.value = task.result?.user?.uid
                    setAction(SIGN_IN_PHONE_AUTH_CREDENTIAL_SUCCESSFULLY)
                } else {
                    // Sign in failed, display a message and update the UI
                    Timber.tag(TAG).d("signInWithPhoneAuthCredential failed: ")
// TODO: 2021/06/11 livedata인 actiion을 observe 못 하고 있음.. activity를 파싱해서 발생하는 문제로 생각됨.. 롧백해봐야함.. 
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        setAction(SIGN_IN_PHONE_AUTH_CREDENTIAL_INVALID)
                    } else {
                        setAction(SIGN_IN_PHONE_AUTH_CREDENTIAL_FAILED)
                    }
                }
            }
        }
    }

    fun resendVerificationCode(
        callbackActivity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
    ) {
        Timber.tag(TAG).d("resendVerificationCode: ")
        tel.value?.let {
            resendVerificationCodeUseCase(
                callbackActivity = callbackActivity,
                callbacks = callbacks,
                firebaseAuth = auth.value!!,
                phoneNumber = tel.value!!,
                resendToken = resendToken.value,
            )
        }
    }

    fun requestLogin() {
        Timber.tag(TAG).d("requestLogin: ")
        signInTestUseCase(tel.value, signInPassword.value ?: signUpPassword.value)
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

    fun signUp() {
        Timber.tag(TAG).d("signUp : ")
        signInUseCase(
            SignInDto(
                uid = uid.value!!,
                ownerName = ownerName.value!!,
                tel = tel.value!!,
                businessType = businessTypes.value!!,
                roadAddress = roadAddress.value!!,
                detailAddress = detailAddress.value!!,
                latitude = latitude.value!!,
                longitude = longitude.value!!,
                serviceArea = serviceAreaToInt.value!!,
                privacyPolicy = agreedPrivacyPolicy.value!!,
                marketingPush = marketingPush.value!!,
                marketingPushAtNight = marketingPush.value!!,
                appPush = appPush.value!!,
                kakaoPush = appPush.value!!,
                smsPush = appPush.value!!,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                Timber.tag(TAG).d("Sign Up Successful : $it")
                requestLogin()
            },
                onError = {
                    Timber.tag(TAG).d("Sign Up Failed : $it")
                    setAction(SIGN_UP_FAILED)
                }
            ).addToDisposable()
    }


    companion object {
        private const val TAG = "SignUpViewModel"
        const val SIGN_UP_FAILED = "SIGN_UP_FAILED"
        const val SIGN_IN_SUCCESSFUL = "SIGN_IN_SUCCESSFUL"
        const val SIGN_IN_FAILED = "SIGN_IN_FAILED"

        const val PHONE_NUMBER_EXIST = "PHONE_NUMBER_EXIST"
        const val PHONE_NUMBER_NOT_EXIST = "PHONE_NUMBER_NOT_EXIST"
        const val CHECK_PHONE_NUMBER_FAILED = "CHECK_PHONE_NUMBER_FAILED"

        const val SIGN_IN_PHONE_AUTH_CREDENTIAL_SUCCESSFULLY =
            "SIGN_IN_PHONE_AUTH_CREDENTIAL_SUCCESSFULLY"
        const val SIGN_IN_PHONE_AUTH_CREDENTIAL_INVALID = "SIGN_IN_PHONE_AUTH_CREDENTIAL_INVALID"
        const val SIGN_IN_PHONE_AUTH_CREDENTIAL_FAILED = "SIGN_IN_PHONE_AUTH_CREDENTIAL_FAILED"

    }
}