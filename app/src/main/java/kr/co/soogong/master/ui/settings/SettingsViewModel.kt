package kr.co.soogong.master.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.AppSharedPreferenceHelper
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class SettingsViewModel(
    private val repository: Repository
) : BaseViewModel() {
    private val _userInfo = repository.getUserInfo(InjectHelper.keyCode ?: "")

    private val _list: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())
    val list: LiveData<List<Notice>>
        get() = _list

    val name: LiveData<String>
        get() = _userInfo.map { it?.name ?: "고객님" }

    val usingPlan: LiveData<String?>
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

    val starCount: LiveData<Double>
        get() = _userInfo.map { it?.starCount ?: 0.0 }

    val reviewsCount: LiveData<Int>
        get() = _userInfo.map { it?.reviewsCount ?: 0 }

    fun requestUserProfile() {
        HttpClient.getUserProfile(InjectHelper.keyCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userInfo ->
                viewModelScope.launch {
                    repository.insertUserInfo(userInfo)
                }
                Timber.tag(TAG).d("requestUserProfile: $userInfo")
            }, {
                Timber.tag(TAG).e("requestUserProfile: $it")
            })
            .addToDisposable()
    }

    fun actionLogout() {
        viewModelScope.launch {
            repository.removeAllRequirement()
            repository.removeAllUserInfo()
            repository.setString(AppSharedPreferenceHelper.BRANCH_KEYCODE, "")
            complete()
        }
    }

    fun getNoticeList() {
        HttpClient.getNoticeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _list.postValue(it)
            }, {
                Timber.tag(TAG).w("getNoticeList: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "SettingsViewModel"
    }
}