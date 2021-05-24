package kr.co.soogong.master.ui.auth.password.find

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.RequestCertificationCodeUseCase
import kr.co.soogong.master.domain.usecase.auth.RequestConfirmCertificationCodeUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val requestCertificationCodeUseCase: RequestCertificationCodeUseCase,
    private val requestConfirmCertificationCodeUseCase: RequestConfirmCertificationCodeUseCase,
) : BaseViewModel() {
    val phoneNumber = MutableLiveData("")
    val certificationCode = MutableLiveData("")

    private var _isEnabled = MutableLiveData(false)
    val isEnabled: LiveData<Boolean>
        get() = _isEnabled

    fun changeEnabled() {
        _isEnabled.value = !_isEnabled.value!!
    }

    fun requestCertificationCode() {
        Timber.tag(TAG).d("requestCertificationCode: ")
        phoneNumber.value?.let {
            requestCertificationCodeUseCase(phoneNumber = it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { },
                    onError = { setAction(CERTIFICATION_CODE_REQUESTED_FAILED) }
                ).addToDisposable()
        }
    }

    fun requestConfirmCertificationCode() {
        Timber.tag(TAG).d("requestConfirmCertificationCode: ")

        // 입력한 인증번호를 확인
        // 인증되면, 다음화면으로
        // 안 되면, alert 표시
        certificationCode.value?.let {
            requestConfirmCertificationCodeUseCase(certificationCode = it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY) },
                    onError = { setAction(CERTIFICATION_CODE_CONFIRMED_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "FindPasswordViewModel"

        const val CERTIFICATION_CODE_REQUESTED_FAILED = "CERTIFICATION_CODE_REQUESTED_FAILED"

        const val CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY =
            "CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY"
        const val CERTIFICATION_CODE_CONFIRMED_FAILED = "CERTIFICATION_CODE_CONFIRMED_FAILED"
    }
}