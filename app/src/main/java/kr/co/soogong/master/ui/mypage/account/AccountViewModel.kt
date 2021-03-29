package kr.co.soogong.master.ui.mypage.account

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
) : BaseViewModel() {

    fun withDraw() {
        Timber.tag(TAG).i("WithDraw Button")
        setAction(WITHDRAW)
    }

    fun passwordAction() {
        Timber.tag(TAG).i("Password Change Button")
        setAction(PASSWORD)
    }

    companion object {
        private const val TAG = "AccountViewModel"
        const val WITHDRAW = "WITHDRAW"
        const val PASSWORD = "PASSWORD"
    }
}