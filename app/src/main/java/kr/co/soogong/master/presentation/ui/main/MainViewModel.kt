package kr.co.soogong.master.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.entity.profile.MasterSettings
import kr.co.soogong.master.domain.usecase.auth.SaveFCMTokenUseCase
import kr.co.soogong.master.domain.usecase.profile.GetMasterSettingsUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMasterSettingsUseCase: GetMasterSettingsUseCase,
    private val saveFCMTokenUseCase: SaveFCMTokenUseCase,
) : BaseViewModel() {
    // 마스터 기본 정보
    val masterSettings = MutableLiveData<MasterSettings>()

    // 네비게이션바 간의 이동을 위한 변수
    val selectedMainTabInMainActivity = MutableLiveData(0)

    // 문의목록 프래그먼트에서 메인탭 간의 이동 위한 변수
    val selectedFilterTabInRequirementFragment = MutableLiveData(0)

    init {
        requestMasterSettings()
        registerFCMToken()
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

    private fun registerFCMToken() {
        Timber.tag(TAG).d("registerFCMToken: ")
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.tag(TAG).w("registerFCMToken is failed: ${task.exception}")
                return@addOnCompleteListener
            }

            viewModelScope.launch {
                try {
                    Timber.tag(TAG).d("registerFCMToken successfully: ")
                    saveFCMTokenUseCase(task.result)
                } catch (e: Exception) {
                    Timber.tag(TAG).e("registerFCMToken successfully: $e")
                }
            }
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}