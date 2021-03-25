package kr.co.soogong.master.ui.main

import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authService: AuthService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
) : BaseViewModel() {
    fun registerFCM() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.tag(TAG).e(task.exception, "OnCompleteListener: getInstanceId failed")
                return@addOnCompleteListener
            }
            val token = task.result
            Timber.tag(TAG).d("OnCompleteListener: $token")
            sendRegistrationToServer(token)
        }
    }

    private fun sendRegistrationToServer(token: String?) {
        authService.updateFCMToken(getMasterKeyCodeUseCase(), fcmKey = token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("sendRegistrationToServer: $it")
            }, {
                Timber.tag(TAG).e("sendRegistrationToServer: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}