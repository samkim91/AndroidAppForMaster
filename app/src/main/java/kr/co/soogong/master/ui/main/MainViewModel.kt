package kr.co.soogong.master.ui.main

import androidx.lifecycle.MutableLiveData
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.SaveFCMTokenUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveFCMTokenUseCase: SaveFCMTokenUseCase,
) : BaseViewModel() {

    // 네비게이션바 간의 이동을 위한 변수
    val selectedMainTabInMainActivity = MutableLiveData(0)

    // 문의목록 프래그먼트에서 메인탭 간의 이동 위한 변수
    val selectedMainTabInRequirementFragment = MutableLiveData(0)

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

    companion object {
        private const val TAG = "MainViewModel"
    }
}