package kr.co.soogong.master.ui.auth.signin

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.DoLoginUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val doLoginUseCase: DoLoginUseCase
) : BaseViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun loginAction() {
        Timber.tag(TAG).d("loginAction: ")

        if (email.value.isNullOrEmpty()) {
            setAction(FAIL_NULL)
            return
        }

        if (password.value.isNullOrEmpty()) {
            setAction(FAIL_NULL)
            return
        }

        doLoginUseCase(email.value, password.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("loginAction: $it")
                    successToSignIn()
                },
                onError = {
                    Timber.tag(TAG).w("loginAction: $it")
                    failToSignIn()
                })
            .addToDisposable()
    }

    private fun successToSignIn() {
        setAction(SUCCESS)
    }

    private fun failToSignIn() {
        setAction(FAIL)
    }

    fun findAction() {
        Timber.tag(TAG).d("findAction: ")
        setAction(FIND)
    }

    companion object {
        private const val TAG = "SignInViewModel"
        const val SUCCESS = "SUCCESS"
        const val FAIL = "FAIL"
        const val FAIL_NULL = "FAIL_NULL"
        const val FIND = "FIND"
    }
}