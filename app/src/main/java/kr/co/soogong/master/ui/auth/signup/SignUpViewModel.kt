package kr.co.soogong.master.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.domain.usecase.auth.*
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.utils.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val checkPhoneNumberDuplicateUseCase: CheckPhoneNumberDuplicateUseCase,
    private val requestCertificationCodeUseCase: RequestCertificationCodeUseCase,
    private val requestConfirmCertificationCodeUseCase: RequestConfirmCertificationCodeUseCase,
) : BaseViewModel() {

    var indicator = MutableLiveData(0)

    // Step 1
    var phoneNumber = MutableLiveData("")

    // Step 2
    var certificationCode = MutableLiveData("")

    // Step 2 sub
    var signInPassword = MutableLiveData("")

    // Step 3
    var signUpPassword = MutableLiveData("")
    var signUpConfirmPassword = MutableLiveData("")

    // Step 4
    var businessRepresentativeName = MutableLiveData("")

    // Step 5
    var businessType = ListLiveData<BusinessType>()

    // Step 6
    var address = MutableLiveData("")
    var subAddress = MutableLiveData("")
    var latitude = MutableLiveData(0.0)
    var longitude = MutableLiveData(0.0)

    // Step 7
    var serviceArea = MutableLiveData("")
    var serviceAreaToInt = MutableLiveData(0)

    // Step 8
    var agreedPrivacyPolicy = MutableLiveData(false)
    var agreedMarketing = MutableLiveData(false)
    var appPush = MutableLiveData(false)


    fun checkPhoneNumberDuplicate() {
        Timber.tag(TAG).d("checkIsIdExistent: ")
        // Todo.. 이미 있는 계정인지 확인
        // 있으면, 로그인으로 안내
        // 없으면, 인증코드 메시지 보내고 입력 액티비티로 안내
        checkPhoneNumberDuplicateUseCase(phoneNumber.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("onSuccess: $it")
                    setAction(PHONE_NUMBER_IS_EXISTENT)
                },
                onError = {
                    Timber.tag(TAG).d("onError: $it")
                    setAction(PHONE_NUMBER_NOT_EXISTENT)
                }
            ).addToDisposable()
    }

    fun requestCertificationCode() {
        Timber.tag(TAG).d("requestCertificationCode: ")
        phoneNumber.value?.let {
            requestCertificationCodeUseCase(phoneNumber = it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(CERTIFICATION_CODE_REQUESTED_SUCCESSFULLY) },
                    onError = { setAction(CERTIFICATION_CODE_REQUESTED_FAILED) }
                ).addToDisposable()
        }
    }

    fun requestConfirmCertificationCode() {
        Timber.tag(TAG).d("requestConfirmCertificationCode: ")

        // 입력한 인증번호를 확인
        // 인증되면, 다음화면으로
        // 안 되면, alert 표시
        certificationCode.value?.let {
            requestConfirmCertificationCodeUseCase(certificationCode = it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY) },
                    onError = { setAction(CERTIFICATION_CODE_CONFIRMED_FAILED) }
                ).addToDisposable()
        }
    }

    fun requestLogin() {
        Timber.tag(TAG).d("requestLogin: ")

        signInUseCase(phoneNumber.value, signInPassword.value ?: signUpPassword.value)
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
        signUpUseCase(
            SignUpInfo(
                phoneNumber = phoneNumber.value!!,
                password = signUpPassword.value!!,
                businessRepresentativeName = businessRepresentativeName.value!!,
                businessType = businessType.value!!,
                address = address.value!!,
                subAddress = subAddress.value!!,
                latitude = latitude.value!!,
                longitude = longitude.value!!,
                serviceArea = serviceAreaToInt.value!!,
                acceptPrivacyPolicy = agreedPrivacyPolicy.value!!,
                appPush = appPush.value!!,
                appPushAtNight = appPush.value!!,
                kakaoAlarm = agreedMarketing.value!!,
                smsAlarm = agreedMarketing.value!!
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

        const val PHONE_NUMBER_IS_EXISTENT = "PHONE_NUMBER_IS_EXISTENT"
        const val PHONE_NUMBER_NOT_EXISTENT = "PHONE_NUMBER_NOT_EXISTENT"

        const val CERTIFICATION_CODE_REQUESTED_SUCCESSFULLY = "CERTIFICATION_CODE_REQUESTED_SUCCESSFULLY"
        const val CERTIFICATION_CODE_REQUESTED_FAILED = "CERTIFICATION_CODE_REQUESTED_FAILED"

        const val CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY = "CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY"
        const val CERTIFICATION_CODE_CONFIRMED_FAILED = "CERTIFICATION_CODE_CONFIRMED_FAILED"



    }
}