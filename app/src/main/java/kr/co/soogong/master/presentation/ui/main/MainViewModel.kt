package kr.co.soogong.master.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.domain.entity.profile.MasterSettings
import kr.co.soogong.master.domain.usecase.auth.SaveFCMTokenUseCase
import kr.co.soogong.master.domain.usecase.profile.GetMasterSettingsUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.presentation.uihelper.main.MainActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveFCMTokenUseCase: SaveFCMTokenUseCase,
    private val getMasterSettingsUseCase: GetMasterSettingsUseCase,
) : BaseViewModel() {
    // 마스터 기본 정보
    val masterSettings = MutableLiveData<MasterSettings>()

    // 네비게이션바 간의 이동을 위한 변수
    val selectedMainTabInMainActivity = MutableLiveData(0)

    // 문의목록 프래그먼트에서 메인탭 간의 이동 위한 변수
    val selectedMainTabInRequirementFragment = MutableLiveData(0)

    init {
        registerFCM()
        requestMasterSettings()
    }

    private fun registerFCM() {
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
                    Timber.tag(TAG).d("sendRegistrationToServer successfully: ")
                },
                onError = {
                    Timber.tag(TAG).e("sendRegistrationToServer failed: $it")
                })
            .addToDisposable()
    }

    fun requestMasterSettings() {
        Timber.tag(TAG).d("requestMasterSettings: ")
        getMasterSettingsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo successful: ")
                    masterSettings.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}