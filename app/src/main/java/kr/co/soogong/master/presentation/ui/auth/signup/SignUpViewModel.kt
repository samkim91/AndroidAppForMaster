package kr.co.soogong.master.presentation.ui.auth.signup

import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.usecase.auth.CheckUserExistentUseCase
import kr.co.soogong.master.domain.usecase.auth.SignUpUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val checkUserExistentUseCase: CheckUserExistentUseCase,
) : BaseViewModel() {

    val totalPages: Int = 2
    val currentPage = MutableLiveData(0)
    val validation = MutableLiveData("")

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
    val termsOfService = MutableLiveData(false)
    val privacyPolicy = MutableLiveData(false)
    val marketingPush = MutableLiveData(false)

    fun checkValidation() {
        Timber.tag(TAG).d("checkValidation: ${currentPage.value}")

        when (currentPage.value) {
            0 -> validation.value = VALIDATE_PHONE_NUMBER
            1 -> validation.value = VALIDATE_OWNER_NAME
        }
    }

    fun checkAll(v: View) {
        Timber.tag(TAG).d("checkAll: ")

        (v as AppCompatCheckBox).isChecked.run {
            termsOfService.value = this
            privacyPolicy.value = this
            marketingPush.value = this
        }
    }

    fun setCurrentPage(page: Int) {
        Timber.tag(TAG).d("setCurrentPage: $page")
        currentPage.value = page
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
                        sendEvent(IS_PHONE_NUMBER_EXIST, it)
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
                projectDtos = emptyList(),
                roadAddress = "",
                detailAddress = "",
                latitude = 0.0f,
                longitude = 0.0f,
                serviceArea = 0,
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

        const val IS_PHONE_NUMBER_EXIST = "PHONE_NUMBER_EXIST"

        const val VALIDATE_PHONE_NUMBER = "VALIDATE_PHONE_NUMBER"
        const val VALIDATE_OWNER_NAME = "VALIDATE_OWNER_NAME"
    }
}