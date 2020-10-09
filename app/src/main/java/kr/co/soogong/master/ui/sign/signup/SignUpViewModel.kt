package kr.co.soogong.master.ui.sign.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class SignUpViewModel : BaseViewModel() {
    private val _list = MutableLiveData<List<String>>()
    val list: LiveData<List<String>>
        get() = _list

    fun sendList(list: List<String>) {
        _list.postValue(list)
    }

    fun signUp(signUpInfo: SignUpInfo) {
        HttpClient.actionSignUp(signUpInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("signUp: $it")
            }, {
                Timber.tag(TAG).w("signUp: ${it.message}")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "SignUpViewModel"
    }
}