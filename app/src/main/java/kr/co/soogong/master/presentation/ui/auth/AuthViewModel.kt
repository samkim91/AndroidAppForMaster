package kr.co.soogong.master.presentation.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.usecase.auth.CheckMasterExistentUseCase
import kr.co.soogong.master.domain.usecase.auth.RequestCertificationCodeUseCase
import kr.co.soogong.master.domain.usecase.auth.VerifyCertificationCodeUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.utility.extension.isValidPhoneNumber
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val checkMasterExistentUseCase: CheckMasterExistentUseCase,
    private val requestCertificationCodeUseCase: RequestCertificationCodeUseCase,
    private val verifyCertificationCodeUseCase: VerifyCertificationCodeUseCase,
) : BaseViewModel() {

    val tel = MutableLiveData("")
    val certificationCode = MutableLiveData("")

    val phoneNumberInputEnable = MutableLiveData(true)

    private fun checkUserExist() {
        Timber.tag(TAG).d("checkUserExist: ")

        setAction(CLEAR_ERROR)

        if (!tel.value.isValidPhoneNumber()) {
            setAction(REQUIRED_TEL)
            return
        }

        checkMasterExistentUseCase(tel.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it) setAction(EXIST_USER) else requestCertificationCode()
                },
                onError = {
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun requestButtonClick() {
        Timber.tag(TAG).d("requestButtonClick: ")

        if (phoneNumberInputEnable.value == true) {
            checkUserExist()
            phoneNumberInputEnable.value = false
        } else {
            tel.value = ""
            phoneNumberInputEnable.value = true
        }
    }

    fun requestAgainButtonClick() {
        Timber.tag(TAG).d("requestAgainButtonClick: ")
        checkUserExist()
    }

    private fun requestCertificationCode() {
        Timber.tag(TAG).d("requestCertificationCode: ")

        viewModelScope.launch {
            try {
                requestCertificationCodeUseCase(tel.value!!)
                setAction(CREDENTIAL_CODE_REQUESTED)
            } catch (e: Exception) {
                setAction(REQUEST_FAILED)
                Timber.tag(TAG).e("changeMarketingPush failed: $e")
            }
        }
    }

    fun verifyCertificationCode() {
        Timber.tag(TAG).d("verifyCertificationCode: ")

        if (!tel.value.isValidPhoneNumber()) {
            setAction(REQUIRED_TEL)
            return
        }

        if (certificationCode.value?.length != 6) {
            setAction(REQUIRED_CODE)
            return
        }

        viewModelScope.launch {
            try {
                val verifiedResult = verifyCertificationCodeUseCase(tel.value!!, certificationCode.value!!)

                if (verifiedResult) setAction(TASK_SUCCESSFUL) else setAction(TASK_FAILED)
            } catch (e: Exception) {
                setAction(TRY_AGAIN)
                Timber.tag(TAG).e("changeMarketingPush failed: $e")
            }
        }
    }

    companion object {

        private val TAG = AuthViewModel::class.java.simpleName

        const val REQUIRED_TEL = "REQUIRED_TEL"
        const val REQUIRED_CODE = "REQUIRED_CODE"

        const val CLEAR_ERROR = "CLEAR_ERROR"

        const val EXIST_USER = "EXIST_USER"
        const val CREDENTIAL_CODE_REQUESTED = "CREDENTIAL_CODE_REQUESTED"

        const val TRY_AGAIN = "TRY_AGAIN"
        const val TASK_SUCCESSFUL = "TASK_SUCCESSFUL"
        const val TASK_FAILED = "TASK_FAILED"
    }
}