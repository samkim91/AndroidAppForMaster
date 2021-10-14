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
                    setAction(GET_PROFILE_FAILED)
                }
            ).addToDisposable()

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
                    onError = { setAction(SAVE_PHONE_NUMBER_FAILED) }
                ).addToDisposable()
        }
    }


    companion object {
        private const val TAG = "EditPhoneNumberViewModel"
        const val GET_PROFILE_FAILED = "GET_PROFILE_FAILED"
        const val SAVE_PHONE_NUMBER_SUCCESSFULLY =
            "SAVE_PHONE_NUMBER_SUCCESSFULLY"
        const val SAVE_PHONE_NUMBER_FAILED =
            "SAVE_PHONE_NUMBER_FAILED"
    }
}