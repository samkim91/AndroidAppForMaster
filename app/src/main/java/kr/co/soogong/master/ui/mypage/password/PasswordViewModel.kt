package kr.co.soogong.master.ui.mypage.password

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class PasswordViewModel : BaseViewModel() {
    fun resetPassword(password: String, confirmPassword: String) {
        HttpClient.resetPassword(InjectHelper.keyCode, password, confirmPassword)
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