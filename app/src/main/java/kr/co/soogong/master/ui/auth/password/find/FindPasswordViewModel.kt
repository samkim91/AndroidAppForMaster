package kr.co.soogong.master.ui.auth.password.find

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(

) : BaseViewModel() {
    val phoneNumber = MutableLiveData("")
    val certificationCode = MutableLiveData("")

    // TODO: 2021/06/10 비밀번호 없어지므로, 삭제 필요

    private var _isEnabled = MutableLiveData(false)
    val isEnabled: LiveData<Boolean>
        get() = _isEnabled

    fun changeEnabled() {
        _isEnabled.value = !_isEnabled.value!!
    }

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
//                    onSuccess = { setAction(CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY) },
//                    onError = { setAction(CERTIFICATION_CODE_CONFIRMED_FAILED) }
//                ).addToDisposable()
//        }
    }

    companion object {
        private const val TAG = "FindPasswordViewModel"

        const val CERTIFICATION_CODE_REQUESTED_FAILED = "CERTIFICATION_CODE_REQUESTED_FAILED"

        const val CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY =
            "CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY"
        const val CERTIFICATION_CODE_CONFIRMED_FAILED = "CERTIFICATION_CODE_CONFIRMED_FAILED"
    }
}