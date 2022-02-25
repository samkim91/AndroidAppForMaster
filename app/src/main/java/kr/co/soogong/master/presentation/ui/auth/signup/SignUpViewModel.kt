package kr.co.soogong.master.presentation.ui.auth.signup

import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.auth.MasterSignUpDto
import kr.co.soogong.master.domain.usecase.auth.SignUpUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : BaseViewModel() {

    val totalPages: Int = 3
    val currentPage = MutableLiveData(0)

    // PhoneNumberFragment
    val tel = MutableLiveData("")
    val uid = MutableLiveData("")

    // OwnerNameFragment
    val ownerName = MutableLiveData("")
    val termsOfService = MutableLiveData(false)
    val privacyPolicy = MutableLiveData(false)
    val marketingPush = MutableLiveData(false)
    val repairInPerson = MutableLiveData(false)

    fun onClickBottomButton() {
        Timber.tag(TAG).d("onClickBottomButton: ${currentPage.value}")

        when (currentPage.value) {
            0 -> sendMessage(VALIDATE_PHONE_NUMBER, "")
            1 -> sendMessage(VALIDATE_OWNER_NAME, "")
            2 -> sendMessage(MOVE_TO_MAIN_ACTIVITY, "")
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

    fun signUp() {
        Timber.tag(TAG).d("signUp : ")
        signUpUseCase(
            MasterSignUpDto(
                uid = uid.value!!,
                ownerName = ownerName.value!!,
                tel = tel.value!!,
                privatePolicy = privacyPolicy.value!!,
                marketingPush = marketingPush.value!!,
                pushAtNight = true,
                repairInPerson = repairInPerson.value!!
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("Sign Up Successful : $it")
                    sendMessage(SIGN_UP_SUCCESSFULLY, "")
                },
                onError = {
                    Timber.tag(TAG).d("Sign Up failed : $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "SignUpViewModel"

        const val BUTTON_ENABLED = "BUTTON_ENABLED"

        const val VALIDATE_PHONE_NUMBER = "VALIDATE_PHONE_NUMBER"
        const val VALIDATE_OWNER_NAME = "VALIDATE_OWNER_NAME"
        const val SIGN_UP_SUCCESSFULLY = "SIGN_UP_SUCCESSFULLY"
        const val MOVE_TO_MAIN_ACTIVITY = "MOVE_TO_MAIN_ACTIVITY"
    }
}