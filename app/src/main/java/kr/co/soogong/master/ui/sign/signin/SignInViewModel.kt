package kr.co.soogong.master.ui.sign.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.domain.AppSharedPreferenceHelper
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class SignInViewModel(
    private val repository: Repository
) : BaseViewModel() {

    fun getUserInfo(email: String, password: String) {
        if (BuildConfig.DEBUG) {
            repository.setString(AppSharedPreferenceHelper.BRANCH_KEYCODE, "e40de2450a27563a")
            successToSignIn()
            return
        }
        HttpClient.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("getUserInfo: $it")
                InjectHelper.keyCode = it
                repository.setString(AppSharedPreferenceHelper.BRANCH_KEYCODE, it)
                successToSignIn()
            }, {
                Timber.tag(TAG).w("getUserInfo: $it")
                failToSignIn()
            })
            .addToDisposable()
    }

    private val _event = MutableLiveData<Event<String>>()
    val event: LiveData<Event<String>>
        get() = _event

    private fun successToSignIn() {
        _event.postValue(Event(SUCCESS))
    }

    private fun failToSignIn() {
        _event.postValue(Event(FAIL))
    }

    companion object {
        private const val TAG = "SignInViewModel"
        const val SUCCESS = "SUCCESS"
        const val FAIL = "FAIL"
    }
}