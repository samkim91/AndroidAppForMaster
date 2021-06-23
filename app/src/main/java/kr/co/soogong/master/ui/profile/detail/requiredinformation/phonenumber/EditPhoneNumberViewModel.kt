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
import kr.co.soogong.master.domain.usecase.profile.SavePhoneNumberUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditPhoneNumberViewModel @Inject constructor(
    private val savePhoneNumberUseCase: SavePhoneNumberUseCase,
) : BaseViewModel() {
    val tel = MutableLiveData("")
    val certificationCode = MutableLiveData("")
    val auth = MutableLiveData(Firebase.auth)
    val phoneAuthCredential = MutableLiveData<PhoneAuthCredential>()
    val storedVerificationId = MutableLiveData("")
    val resendToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()
    val uid = MutableLiveData("")

    private var _isEnabled = MutableLiveData(false)
    val isEnabled: LiveData<Boolean>
        get() = _isEnabled

    fun changeEnabled() {
        _isEnabled.value = !_isEnabled.value!!
    }

    fun savePhoneNumber() {
        // TODO: 2021/06/14 Firebase admin SDK를 이용해서, 현재 유저의 전화번호를 새 번호로 업데이트하고, DB에도 넣어줘야함.
        Timber.tag(TAG).d("savePhoneNumber: ")

        tel.value?.let {
            savePhoneNumberUseCase(it)
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
        const val SAVE_PHONE_NUMBER_SUCCESSFULLY =
            "SAVE_PHONE_NUMBER_SUCCESSFULLY"
        const val SAVE_PHONE_NUMBER_FAILED =
            "SAVE_PHONE_NUMBER_FAILED"
    }
}