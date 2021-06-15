package kr.co.soogong.master.ui.mypage.account

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
) : BaseViewModel() {

    fun withdrawal() {
        Timber.tag(TAG).i("withdrawal Button")
        setAction(WITHDRAWAL)
    }

    companion object {
        private const val TAG = "AccountViewModel"
        const val WITHDRAWAL = "WITHDRAWAL"
        const val PASSWORD = "PASSWORD"
    }
}