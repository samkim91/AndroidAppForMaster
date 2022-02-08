package kr.co.soogong.master.presentation.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.common.major.ProjectDto
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.entity.common.DropdownItemList
import kr.co.soogong.master.domain.entity.common.major.Project
import kr.co.soogong.master.domain.usecase.auth.CheckUserExistentUseCase
import kr.co.soogong.master.domain.usecase.auth.SignUpUseCase
import kr.co.soogong.master.presentation.SIGN_UP_STEPS
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val checkUserExistentUseCase: CheckUserExistentUseCase,
) : BaseViewModel() {

    val pagesCount = SIGN_UP_STEPS
    val indicator = MutableLiveData(0)
    val validation = MutableLiveData<String>()

    // PhoneNumberFragment
    val tel = MutableLiveData("")
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
    val projects = ListLiveData<Project>()

    // AddressFragment
    val roadAddress = MutableLiveData("")
    val detailAddress = MutableLiveData("")
    val latitude = MutableLiveData(0.0)
    val longitude = MutableLiveData(0.0)

    // ServiceAreaFragment
    val serviceAreas: List<Pair<String, Int>> = DropdownItemList.serviceAreas
    val serviceArea = MutableLiveData(Pair("", 0))

    // RepairInPersonFragment
    val repairInPerson = MutableLiveData(false)

    // PrivatePolicyFragment
    val privacyPolicy = MutableLiveData(false)
    val marketingPush = MutableLiveData(false)

    fun checkValidation() {
        Timber.tag(TAG).d("checkValidation: ${indicator.value}")

        when (indicator.value) {
            0 -> validation.postValue(VALIDATE_PHONE_NUMBER)
            1 -> validation.postValue(VALIDATE_OWNER_NAME)
            2 -> validation.postValue(VALIDATE_MAJOR)
            3 -> validation.postValue(VALIDATE_ADDRESS)
            4 -> validation.postValue(VALIDATE_SERVICE_AREA)
            5 -> validation.postValue(VALIDATE_REPAIR_IN_PERSON)
            6 -> validation.postValue(VALIDATE_AGREEMENT)
        }
    }


    fun moveToNext() {
        Timber.tag(TAG).d("moveToNext: ")
        indicator.value = indicator.value?.plus(1)
    }

    fun moveToPrevious() {
        Timber.tag(TAG).d("moveToPrevious: ")
        indicator.value = indicator.value?.minus(1)
    }

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
                        setAction(REQUEST_FAILED)
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
                projectDtos = ProjectDto.fromProjects(projects.value),
                roadAddress = roadAddress.value,
                detailAddress = detailAddress.value,
                latitude = latitude.value?.toFloat(),
                longitude = longitude.value?.toFloat(),
                serviceArea = serviceArea.value?.second,
                privatePolicy = privacyPolicy.value,
                marketingPush = marketingPush.value,
                pushAtNight = true,
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
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "SignUpViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val SIGN_UP_SUCCESSFULLY = "SIGN_UP_SUCCESSFULLY"

        const val PHONE_NUMBER_EXIST = "PHONE_NUMBER_EXIST"
        const val PHONE_NUMBER_NOT_EXIST = "PHONE_NUMBER_NOT_EXIST"

        const val VALIDATE_PHONE_NUMBER = "VALIDATE_PHONE_NUMBER"
        const val VALIDATE_OWNER_NAME = "VALIDATE_OWNER_NAME"
        const val VALIDATE_MAJOR = "VALIDATE_MAJOR"
        const val VALIDATE_ADDRESS = "VALIDATE_ADDRESS"
        const val VALIDATE_SERVICE_AREA = "VALIDATE_SERVICE_AREA"
        const val VALIDATE_REPAIR_IN_PERSON = "VALIDATE_REPAIR_IN_PERSON"
        const val VALIDATE_AGREEMENT = "VALIDATE_AGREEMENT"
    }
}