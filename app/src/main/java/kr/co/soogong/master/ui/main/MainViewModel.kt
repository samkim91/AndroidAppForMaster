package kr.co.soogong.master.ui.main

import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.SaveFCMTokenUseCase
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveFCMTokenUseCase: SaveFCMTokenUseCase,
    private val getMasterSimpleInfoUseCase: GetMasterSimpleInfoUseCase,
) : BaseViewModel() {

    fun registerFCM() {
        Timber.tag(TAG).d("registerFCM: ")

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.tag(TAG).e(task.exception, "OnCompleteListener: getInstanceId failed")
                return@addOnCompleteListener
            }
            val token = task.result
            Timber.tag(TAG).d("OnCompleteListener: $token")
            token?.let {
                sendRegistrationToServer(it)
            }
        }
    }

    private fun sendRegistrationToServer(token: String) {
        Timber.tag(TAG).d("sendRegistrationToServer: ")

        saveFCMTokenUseCase(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("sendRegistrationToServer successfully: $it")
                },
                onError = {
                    Timber.tag(TAG).e("sendRegistrationToServer failed: $it")
                })
            .addToDisposable()
    }

    fun getMasterSimpleInfo() {
        Timber.tag(TAG).d("getMasterSimpleInfo: ")

        getMasterSimpleInfoUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("getMasterSimpleInfoUseCase successfully: $it")
                },
                onError = {
                    Timber.tag(TAG).d("getMasterSimpleInfoUseCase failed: $it")
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}