package kr.co.soogong.master.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.profile.Profile
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.GetUserInfoUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile?>()
    val profile: LiveData<Profile?>
        get() = _profile

    val profileImage = MutableLiveData(Uri.EMPTY)

    fun requestProfile(){
        Timber.tag(TAG).d("requestProfile: ")
        viewModelScope.launch {
            _profile.value = getProfileUseCase()
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}