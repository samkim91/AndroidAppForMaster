package kr.co.soogong.master.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.domain.usecase.DoResetUseCase
import kr.co.soogong.master.domain.usecase.GetNoticeListUseCase
import kr.co.soogong.master.domain.usecase.GetUserInfoUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getNoticeListUseCase: GetNoticeListUseCase,
    private val doResetUseCase: DoResetUseCase,
) : BaseViewModel() {
    private val _user = MutableLiveData<User?>(null)
    val user: LiveData<User?>
        get() = _user

    private fun getUserProfile() {
        viewModelScope.launch {
            _user.value = getUserInfoUseCase()
        }
    }

    private val _noticeList: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())
    val noticeList: LiveData<List<Notice>>
        get() = _noticeList

    private fun getNoticeList() {
        getNoticeListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _noticeList.postValue(it.sortedByDescending { it.date }.takeLast(3))
            }, {
                Timber.tag(TAG).w("getNoticeList: $it")
            })
            .addToDisposable()
    }

    fun initialize() {
        getNoticeList()
        getUserProfile()
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
            doResetUseCase()
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