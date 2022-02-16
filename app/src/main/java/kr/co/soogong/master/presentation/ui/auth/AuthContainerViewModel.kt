package kr.co.soogong.master.presentation.ui.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthContainerViewModel @Inject constructor() : BaseViewModel() {
    fun moveToSignUp() {
        Timber.tag(TAG).d("moveToSignUp: ")
        setAction(SIGN_UP)
    }

    fun moveToSignIn() {
        Timber.tag(TAG).d("moveToSignIn: ")
        setAction(SIGN_IN)
    }

    companion object {
        private const val TAG = "AuthContainerViewModel"
        const val SIGN_UP = "SIGN_UP"
        const val SIGN_IN = "SIGN_IN"
    }
}