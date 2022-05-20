package kr.co.soogong.master.presentation.ui.auth.signin

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.SignInUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel() {

    fun signInByTel(tel: String) {
        Timber.tag(TAG).d("requestSignIn: $tel")

        signInUseCase("", tel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestSignIn successToSignIn: $it")
                    setAction(SIGN_IN_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("requestSignIn failToSignIn: $it ")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private val TAG = SignInViewModel::class.java.simpleName

        const val SIGN_IN_SUCCESSFULLY = "SIGN_IN_SUCCESSFULLY"
    }
}