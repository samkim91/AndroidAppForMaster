package kr.co.soogong.master.presentation.ui.preferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.entity.preferences.Notice
import kr.co.soogong.master.domain.entity.profile.Profile
import kr.co.soogong.master.domain.usecase.preferences.SignOutUseCase
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile?>(null)
    val profile: LiveData<Profile?>
        get() = _profile

    private val _noticeList: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())
    val noticeList: LiveData<List<Notice>>
        get() = _noticeList

    val version = MutableLiveData("")

    fun requestUserProfile() {
        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { _profile.value = it },
                onError = { }
            ).addToDisposable()
    }

    fun moveToSettingAlarm() {
        Timber.tag(TAG).i("moveToSettingAlarm")
        setAction(ALARM)
    }

    fun moveToNotice() {
        Timber.tag(TAG).i("moveToNotice")
        setAction(NOTICE)
    }

    fun requestLogout() {
        Timber.tag(TAG).i("requestLogout")
        setAction(REQUEST_LOGOUT)
    }

    fun logout() {
        Timber.tag(TAG).i("logout")
        viewModelScope.launch {
            signOutUseCase()
            setAction(LOGOUT)
        }
    }

    fun updateVersion() {
        Timber.tag(TAG).i("updateVersion")
        setAction(VERSION)
    }

    fun moveToCustomerService() {
        Timber.tag(TAG).i("moveToCustomerService")
        setAction(CUSTOMER_SERVICE)
    }

    //    fun accountSettingAction() {
//        Timber.tag(TAG).i("Account Setting Button")
//        setAction(ACCOUNT)
//    }

    companion object {
        private const val TAG = "PreferencesViewModel"
        const val NOTICE = "NOTICE"
        const val ALARM = "ALARM"
        const val REQUEST_LOGOUT = "REQUEST_LOGOUT"
        const val LOGOUT = "LOGOUT"
        const val VERSION = "VERSION"
        const val CUSTOMER_SERVICE = "CUSTOMER_SERVICE"
    }
}