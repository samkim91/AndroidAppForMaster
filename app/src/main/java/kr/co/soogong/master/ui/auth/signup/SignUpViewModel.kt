package kr.co.soogong.master.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MajorDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.domain.usecase.auth.CheckUserExistentUseCase
import kr.co.soogong.master.domain.usecase.auth.SignUpUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val checkUserExistentUseCase: CheckUserExistentUseCase,
) : BaseViewModel() {

    val indicator = MutableLiveData(0)

    // PhoneNumberFragment
    val tel = MutableLiveData("")

    // AuthFragment
    val certificationCode = MutableLiveData("")

    // Firebase Auth
    val auth = MutableLiveData(Firebase.auth)
    val phoneAuthCredential = MutableLiveData<PhoneAuthCredential>()
    val storedVerificationId = MutableLiveData("")
    val resendToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()
    val uid = MutableLiveData("")

    // OwnerNameFragment
    val ownerName = MutableLiveData("")

    // MajorFragment
    val majors = ListLiveData<Major>()

    // AddressFragment
    val roadAddress = MutableLiveData("")
    val detailAddress = MutableLiveData("")
    val latitude = MutableLiveData(0.0)
    val longitude = MutableLiveData(0.0)

    // ServiceAreaFragment
    val serviceArea = MutableLiveData("")
    val serviceAreaToInt = MutableLiveData(0)

    // RepairInPersonFragment
    val repairInPerson = MutableLiveData(false)

    // PrivatePolicyFragment
    val privacyPolicy = MutableLiveData(false)
    val marketingPush = MutableLiveData(false)


    fun checkUserExist() {
        Timber.tag(TAG).d("checkUserExist: ")

        tel.value?.let { tel ->
            checkUserExistentUseCase(tel)
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

    fun signUp() {
        Timber.tag(TAG).d("signUp : ")
        signUpUseCase(
            MasterDto(
                id = null,
                uid = uid.value,
                ownerName = ownerName.value,
                tel = tel.value,
                majors = MajorDto.fromMajorList(majors.value?.toList()),
                roadAddress = roadAddress.value,
                detailAddress = detailAddress.value,
                latitude = latitude.value?.toFloat(),
                longitude = longitude.value?.toFloat(),
                serviceArea = serviceAreaToInt.value,
                privatePolicy = privacyPolicy.value,
                marketingPush = marketingPush.value,
                marketingPushAtNight = marketingPush.value,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                Timber.tag(TAG).d("Sign Up Successful : $it")
                setAction(SIGN_UP_SUCCESSFULLY)
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
        const val SIGN_UP_SUCCESSFULLY = "SIGN_UP_SUCCESSFULLY"

        const val PHONE_NUMBER_EXIST = "PHONE_NUMBER_EXIST"
        const val PHONE_NUMBER_NOT_EXIST = "PHONE_NUMBER_NOT_EXIST"
        const val CHECK_PHONE_NUMBER_FAILED = "CHECK_PHONE_NUMBER_FAILED"

    }
}