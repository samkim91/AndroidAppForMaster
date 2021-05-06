package kr.co.soogong.master.ui.profile.edit.withcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.domain.usecase.GetUserInfoUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditProfileWithCardViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel() {
    private val _userInfo = MutableLiveData<User?>(null)
    val userInfo: LiveData<User?>
        get() = _userInfo

    fun requestUserProfile() {
        Timber.tag(TAG).d("requestUserProfile: ")
        viewModelScope.launch {
            _userInfo.value = getUserInfoUseCase()
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}