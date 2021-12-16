package kr.co.soogong.master.ui.preferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.mypage.GetNoticeListUseCase
import kr.co.soogong.master.domain.usecase.mypage.SignOutUseCase
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getNoticeListUseCase: GetNoticeListUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile?>(null)
    val profile: LiveData<Profile?>
        get() = _profile

    private val _noticeList: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())
    val noticeList: LiveData<List<Notice>>
        get() = _noticeList

    val version = MutableLiveData("")

    fun getData() {
        requestNoticeList()
        requestUserProfile()
    }

    private fun requestUserProfile() {
        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { _profile.value = it },
                onError = { }
            ).addToDisposable()
    }

    private fun requestNoticeList() {
        getNoticeListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError =
                {
                    Timber.tag(TAG).w("getNoticeList: $it")
                },
                onNext = { list ->
                    _noticeList.postValue(list.take(3))
                },
                onComplete = {}
            )
            .addToDisposable()
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

    fun kakaoAction() {
        Timber.tag(TAG).i("KAKAO Button")
        setAction(KAKAO)
    }

//    fun callToSoogong() {
//        Timber.tag(TAG).i("callToSoogong")
//        setAction(CALL)
//    }

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

        const val KAKAO = "KAKAO"
        //        const val ACCOUNT = "ACCOUNT"
        //        const val CALL = "CALL"
    }
}