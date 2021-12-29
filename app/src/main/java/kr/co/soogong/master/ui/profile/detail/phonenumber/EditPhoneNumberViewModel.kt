package kr.co.soogong.master.ui.profile.detail.phonenumber

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
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.auth.CheckUserExistentUseCase
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.utility.PhoneNumberHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditPhoneNumberViewModel @Inject constructor(
    private val checkUserExistentUseCase: CheckUserExistentUseCase,
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val tel = MutableLiveData("")
    val certificationCode = MutableLiveData("")
    val auth = MutableLiveData(Firebase.auth)
    val phoneAuthCredential = MutableLiveData<PhoneAuthCredential>()
    val storedVerificationId = MutableLiveData("")
    val resendToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()
    val uid = MutableLiveData("")

    fun requestProfile() {
        Timber.tag(TAG).d("requestProfile: ")

        requestProfile {
            profile.value = it
        }
    }

    fun checkUserExist() {
        Timber.tag(TAG).d("checkUserExist: ")

        checkUserExistentUseCase(tel.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("onSuccess: $it")
                    if (it) setAction(PHONE_NUMBER_EXIST) else setAction(
                        PHONE_NUMBER_NOT_EXIST)
                },
                onError = {
                    Timber.tag(TAG).d("onError: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun savePhoneNumber() {
        Timber.tag(TAG).d("savePhoneNumber: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                tel = PhoneNumberHelper.toGlobalNumber(tel.value!!),
            )
        )
    }

    companion object {
        private const val TAG = "EditPhoneNumberViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val PHONE_NUMBER_EXIST = "PHONE_NUMBER_EXIST"
        const val PHONE_NUMBER_NOT_EXIST = "PHONE_NUMBER_NOT_EXIST"
    }
}