package kr.co.soogong.master.ui.mypage

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
class MyPageViewModel @Inject constructor(
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

    fun initialize() {
        requestNoticeList()
        requestUserProfile()
    }

    fun alarmSettingAction() {
        Timber.tag(TAG).i("Alarm Setting Button")
        setAction(ALARM)
    }

    fun accountSettingAction() {
        Timber.tag(TAG).i("Account Setting Button")
        setAction(ACCOUNT)
    }

    fun noticeViewAction() {
        Timber.tag(TAG).i("Notice View Button")
        setAction(NOTICE)
    }

    fun callAction() {
        Timber.tag(TAG).i("CALL Button")
        setAction(CALL)
    }

    fun kakaoAction() {
        Timber.tag(TAG).i("KAKAO Button")
        setAction(KAKAO)
    }

    fun logout() {
        Timber.tag(TAG).i("Logout Button")
        viewModelScope.launch {
            signOutUseCase()
            setAction(LOGOUT)
        }
    }

    companion object {
        private const val TAG = "MyPageViewModel"
        const val ACCOUNT = "ACCOUNT"
        const val ALARM = "ALARM"
        const val NOTICE = "NOTICE"
        const val CALL = "CALL"
        const val KAKAO = "KAKAO"
        const val LOGOUT = "LOGOUT"
    }
}