package kr.co.soogong.master.ui.profile.detail.requiredinformation.phonenumber

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
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.auth.CheckUserExistentUseCase
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.PhoneNumberHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditPhoneNumberViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
    private val checkUserExistentUseCase: CheckUserExistentUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()

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

    fun requestProfile() {
        Timber.tag(TAG).d("requestProfile: ")

        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    _profile.value = profile
                },
                onError = {
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()

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
                        if (it) setAction(PHONE_NUMBER_EXIST) else setAction(
                            PHONE_NUMBER_NOT_EXIST)
                    },
                    onError = {
                        Timber.tag(TAG).d("onError: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun savePhoneNumber() {
        Timber.tag(TAG).d("savePhoneNumber: ")

        tel.value?.let {
            saveMasterUseCase(
                MasterDto(
                    id = _profile.value?.id,
                    uid = _profile.value?.uid,
                    tel = PhoneNumberHelper.toGlobalNumber(it),
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_PHONE_NUMBER_SUCCESSFULLY) },
                    onError = { setAction(REQUEST_FAILED) }
                ).addToDisposable()
        }
    }


    companion object {
        private const val TAG = "EditPhoneNumberViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val PHONE_NUMBER_EXIST = "PHONE_NUMBER_EXIST"
        const val PHONE_NUMBER_NOT_EXIST = "PHONE_NUMBER_NOT_EXIST"
        const val SAVE_PHONE_NUMBER_SUCCESSFULLY =
            "SAVE_PHONE_NUMBER_SUCCESSFULLY"
    }
}