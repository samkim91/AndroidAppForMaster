package kr.co.soogong.master.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.domain.user.UserDao
import kr.co.soogong.master.network.UserService
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDao: UserDao,
    private val userService: UserService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
) : BaseViewModel() {
    val userInfo = userDao.getItem(getMasterKeyCodeUseCase() ?: "")

    val name: LiveData<String?>
        get() = userInfo.map { it?.name }

    val usingPlan: LiveData<String?>
        get() = userInfo.map {
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
        get() = userInfo.map { it?.introduction }

    val address: LiveData<String?>
        get() = userInfo.map { it?.address }

    val detailAddress: LiveData<String?>
        get() = userInfo.map { it?.detailAddress }

    val description: LiveData<String?>
        get() = userInfo.map { it?.description }

    val categories: LiveData<List<String>?>
        get() = userInfo.map { it?.categories }

    val starCount: LiveData<Double?>
        get() = userInfo.map { it?.starCount }

    val reviewsCount: LiveData<Int?>
        get() = userInfo.map { it?.reviewsCount }

    val position: LiveData<String?>
        get() = userInfo.map {
            if (it?.positions?.isNotEmpty() == true) {
                return@map it.positions[0]
            } else {
                return@map null
            }
        }

    val positions: LiveData<List<String>?>
        get() = userInfo.map {
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

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}