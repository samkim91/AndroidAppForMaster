package kr.co.soogong.master.ui.auth.password.change

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.ChangePasswordUseCase
import kr.co.soogong.master.domain.usecase.auth.SignInTestUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val signInTestUseCase: SignInTestUseCase,
) : BaseViewModel() {
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    fun requestChangePassword() {
        Timber.tag(TAG).d("requestChangePassword: ")
        password.value?.let { password ->
            confirmPassword.value?.let { confirmPassword ->
                changePasswordUseCase(password, confirmPassword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onSuccess = { setAction(PASSWORD_CHANGED_SUCCESSFULLY) },
                        onError = { setAction(PASSWORD_CHANGED_FAILED) }
                    ).addToDisposable()
            }
        }
    }

    fun signIn(id: String) {
        signInTestUseCase(id, password.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SIGN_IN_SUCCESSFULLY) },
                onError = { setAction(SIGN_IN_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ChangePasswordViewModel"

        const val PASSWORD_CHANGED_SUCCESSFULLY = "PASSWORD_CHANGED_SUCCESSFULLY"
        const val PASSWORD_CHANGED_FAILED = "PASSWORD_CHANGED_FAILED"

        const val SIGN_IN_SUCCESSFULLY = "SIGN_IN_SUCCESSFULLY"
        const val SIGN_IN_FAILED = "SIGN_IN_FAILED"
    }
}