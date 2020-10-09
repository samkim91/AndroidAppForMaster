package kr.co.soogong.master.ui.main

import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class MainViewModel : BaseViewModel() {
    fun registerFCM() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { tasks ->
            if (!tasks.isSuccessful) {
                Timber.tag(TAG).e(tasks.exception, "onComplete: getInstanceId failed")
                return@addOnCompleteListener
            }
            val token = tasks.result?.token
            Timber.tag(TAG).d("OnCompleteListener: $token")
            sendRegistrationToServer(token)
        }
    }

    private fun sendRegistrationToServer(token: String?) {
        token?.let { token ->
            HttpClient.updateFCMToken(InjectHelper.keyCode, fcmKey = token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.tag(TAG).d("sendRegistrationToServer: $it")
                }, {
                    Timber.tag(TAG).e("sendRegistrationToServer: $it")
                })
                .addToDisposable()
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}