package kr.co.soogong.master.ui.sign.signin

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.AppSharedPreferenceHelper
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class SignInViewModel(
    private val repository: Repository
) : BaseViewModel() {

    fun getUserInfo(email: String, password: String) {
        HttpClient.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("getUserInfo: $it")
                InjectHelper.keyCode = it
                repository.setString(AppSharedPreferenceHelper.BRANCH_KEYCODE, it)
                complete()
            }, {
                Timber.tag(TAG).w("getUserInfo: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "SignInViewModel"
    }
}