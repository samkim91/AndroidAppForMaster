package kr.co.soogong.master.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.GetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.auth.SetFCMTokenUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setFCMTokenUseCase: SetFCMTokenUseCase,
    val getMasterApprovalUseCase: GetMasterApprovalUseCase,
) : BaseViewModel() {
    private val _isApprovedMaster = MutableLiveData<Boolean>(getMasterApprovalUseCase())
    val isApprovedMaster: LiveData<Boolean>
        get() = _isApprovedMaster

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
        setFCMTokenUseCase(token)
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

    companion object {
        private const val TAG = "MainViewModel"
    }
}