package kr.co.soogong.master.ui.profile.edit.requiredinformation.phonenumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.RequestVerificationCodeUseCase
import kr.co.soogong.master.domain.usecase.auth.GetPhoneAuthCredentialUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePhoneNumberUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditPhoneNumberViewModel @Inject constructor(
    private val requestVerificationCodeUseCase: RequestVerificationCodeUseCase,
    private val getPhoneAuthCredentialUseCase: GetPhoneAuthCredentialUseCase,
    private val savePhoneNumberUseCase: SavePhoneNumberUseCase,
) : BaseViewModel() {
    val phoneNumber = MutableLiveData("")
    val certificationCode = MutableLiveData("")

    private var _isEnabled = MutableLiveData(false)
    val isEnabled: LiveData<Boolean>
        get() = _isEnabled

    fun changeEnabled() {
        _isEnabled.value = !_isEnabled.value!!
    }

    // TODO: 2021/06/10 firebase로 휴대폰 번호 바꾸는 거 확인..

    fun requestCertificationCode() {
        Timber.tag(TAG).d("requestCertificationCode: ")
//        phoneNumber.value?.let {
//            requestVerificationCodeUseCase(phoneNumber = it)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeBy(
//                    onSuccess = { },
//                    onError = { setAction(CERTIFICATION_CODE_REQUESTED_FAILED) }
//                ).addToDisposable()
//        }
    }

    fun requestConfirmCertificationCode() {
        Timber.tag(TAG).d("requestConfirmCertificationCode: ")

        // 입력한 인증번호를 확인
        // 인증되면, 다음화면으로
        // 안 되면, alert 표시
//        certificationCode.value?.let {
//            getPhoneAuthCredentialUseCase(certificationCode = it)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeBy(
//                    onSuccess = { savePhoneNumber() },
//                    onError = { setAction(CERTIFICATION_CODE_CONFIRMED_FAILED) }
//                ).addToDisposable()
//        }
    }

    private fun savePhoneNumber() {
        Timber.tag(TAG).d("savePhoneNumber: ")

        phoneNumber.value?.let {
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

        const val CERTIFICATION_CODE_REQUESTED_FAILED = "CERTIFICATION_CODE_REQUESTED_FAILED"
        const val CERTIFICATION_CODE_CONFIRMED_FAILED = "CERTIFICATION_CODE_CONFIRMED_FAILED"

        const val SAVE_PHONE_NUMBER_SUCCESSFULLY =
            "SAVE_BUSINESS_REPRESENTATIVE_NAME_SUCCESSFULLY"
        const val SAVE_PHONE_NUMBER_FAILED =
            "SAVE_BUSINESS_REPRESENTATIVE_NAME_FAILED"
    }
}