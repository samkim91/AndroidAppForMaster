package kr.co.soogong.master.ui.mypage.password

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.DoPasswordChangeUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val doPasswordChangeUseCase: DoPasswordChangeUseCase
) : BaseViewModel() {
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    fun passwordChangeAction() {
        Timber.tag(TAG).d("passwordChangeAction: ")
        if (password.value.isNullOrEmpty() || confirmPassword.value.isNullOrEmpty()) {
            setAction(FAIL_PASSWORD)
        }
        if (password.value == confirmPassword.value) {
            setAction(FAIL_PASSWORD_NOT_MATCH)
        }
        doPasswordChangeUseCase(password.value, confirmPassword.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("resetPassword: $it")
                    setAction(SUCCESS_PASSWORD)
                },
                onError = {
                    Timber.tag(TAG).w("resetPassword: $it")
                })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "PasswordViewModel"
        const val SUCCESS_PASSWORD = "SUCCESS_PASSWORD"
        const val FAIL_PASSWORD = "FAIL_PASSWORD"
        const val FAIL_PASSWORD_NOT_MATCH = "FAIL_PASSWORD_NOT_MATCH"
    }
}