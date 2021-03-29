package kr.co.soogong.master.ui.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignMainViewModel @Inject constructor() : BaseViewModel() {
    fun signUpAction() {
        Timber.tag(TAG).d("signUpAction: ")
        setAction(SIGNUP)
    }

    fun signInAction() {
        Timber.tag(TAG).d("signInAction: ")
        setAction(SIGNIN)
    }

    companion object {
        private const val TAG = "SignMainViewModel"
        const val SIGNUP = "SIGNUP"
        const val SIGNIN = "SIGNIN"
    }
}