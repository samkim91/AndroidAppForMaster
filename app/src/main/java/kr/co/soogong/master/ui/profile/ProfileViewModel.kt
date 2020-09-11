package kr.co.soogong.master.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class ProfileViewModel(private val repository: Repository) : BaseViewModel() {
    private val _userInfo = repository.getUserInfo("d3899f668347aa1b")

    val name: LiveData<String?>
        get() = _userInfo.map { it?.name }

    val introduction: LiveData<String?>
        get() = _userInfo.map { it?.introduction }

    fun requestUserProfile() {
        HttpClient.getUserProfile()
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