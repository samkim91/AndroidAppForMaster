package kr.co.soogong.master.ui.auth.signin

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.SignInTestUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.validation.ValidationHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInTestUseCase: SignInTestUseCase
) : BaseViewModel() {
    val id = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun loginAction() {
        Timber.tag(TAG).d("loginAction: ")

        if (id.value.isNullOrEmpty() || !ValidationHelper.isNumberOnly(id.value ?: "")) {
            setAction(INVALID_INPUT)
            return
        }

        if (password.value.isNullOrEmpty()) {
            setAction(INVALID_INPUT)
            return
        }

        signInTestUseCase(id.value, password.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    successToSignIn()
                },
                onError = {
                    failToSignIn()
                })
            .addToDisposable()
    }

    private fun successToSignIn() {
        Timber.tag(TAG).d("successToSignIn: ")
        setAction(SUCCESS)
    }

    private fun failToSignIn() {
        Timber.tag(TAG).d("failToSignIn: ")
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
        const val INVALID_INPUT = "INVALID_INPUT"
        const val FIND = "FIND"
    }
}