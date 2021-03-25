package kr.co.soogong.master.ui.mypage.password

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val authService: AuthService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
) : BaseViewModel() {
    fun resetPassword(password: String, confirmPassword: String) {
        authService.resetPassword(getMasterKeyCodeUseCase(), password, confirmPassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("resetPassword: $it")
                complete()
            }, {
                Timber.tag(TAG).w("resetPassword: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "PasswordViewModel"
    }
}