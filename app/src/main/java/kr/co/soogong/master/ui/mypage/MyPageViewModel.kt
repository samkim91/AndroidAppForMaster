package kr.co.soogong.master.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.usecase.DoResetUseCase
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.domain.usecase.SetMasterKeyCodeUseCase
import kr.co.soogong.master.domain.user.UserDao
import kr.co.soogong.master.network.NoticeService
import kr.co.soogong.master.network.UserService
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userDao: UserDao,
    private val userService: UserService,
    private val noticeService: NoticeService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
    private val doResetUseCase: DoResetUseCase
) : BaseViewModel() {
    private val _userInfo = userDao.getItem(getMasterKeyCodeUseCase() ?: "")

    private val _list: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())
    val list: LiveData<List<Notice>>
        get() = _list

    val name: LiveData<String>
        get() = _userInfo.map { it?.name ?: "고객님" }

    val email: LiveData<String?>
        get() = _userInfo.map {
            when {
                it?.usingPlan.isNullOrEmpty() -> {
                    ""
                }
                it?.usingPlan == "free" -> {
                    "무료 플랜"
                }
                else -> {
                    "유료 플랜"
                }
            }
        }

    fun requestUserProfile() {
        userService.getUserProfile(getMasterKeyCodeUseCase())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userInfo ->
                viewModelScope.launch {
                    userDao.insert(userInfo)
                }
                Timber.tag(TAG).d("requestUserProfile: $userInfo")
            }, {
                Timber.tag(TAG).e("requestUserProfile: $it")
            })
            .addToDisposable()
    }

    fun actionLogout() {
        viewModelScope.launch {
            doResetUseCase()
            complete()
        }
    }

    fun getNoticeList() {
        noticeService.getNoticeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _list.postValue(it.sortedByDescending { it.date }.takeLast(3))
            }, {
                Timber.tag(TAG).w("getNoticeList: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "MyPageViewModel"
    }
}