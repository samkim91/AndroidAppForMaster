package kr.co.soogong.master.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val httpClient: HttpClient
) : BaseViewModel() {
    private val _userInfo = repository.getUserInfo(InjectHelper.keyCode ?: "")

    val name: LiveData<String?>
        get() = _userInfo.map { it?.name }

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

    val introduction: LiveData<String?>
        get() = _userInfo.map { it?.introduction }

    val address: LiveData<String?>
        get() = _userInfo.map { it?.address }

    val detailAddress: LiveData<String?>
        get() = _userInfo.map { it?.detailAddress }

    val description: LiveData<String?>
        get() = _userInfo.map { it?.description }

    val categories: LiveData<List<String>?>
        get() = _userInfo.map { it?.categories }

    val starCount: LiveData<Double?>
        get() = _userInfo.map { it?.starCount }

    val reviewsCount: LiveData<Int?>
        get() = _userInfo.map { it?.reviewsCount }

    val position: LiveData<String?>
        get() = _userInfo.map {
            if (it?.positions?.isNotEmpty() == true) {
                return@map it.positions[0]
            } else {
                return@map null
            }
        }

    val positions: LiveData<List<String>?>
        get() = _userInfo.map {
            if (it?.positions?.isNotEmpty() == true) {
                if (it.positions.size > 1) {
                    return@map it.positions.drop(1)
                } else {
                    return@map emptyList()
                }
            } else {
                return@map emptyList()
            }
        }

    fun requestUserProfile() {
        httpClient.getUserProfile(InjectHelper.keyCode)
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

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}