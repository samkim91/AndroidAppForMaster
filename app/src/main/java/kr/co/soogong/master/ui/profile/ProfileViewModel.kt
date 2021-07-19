package kr.co.soogong.master.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetMasterUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMasterUseCase: GetMasterUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile?>()
    val profile: LiveData<Profile?>
        get() = _profile

    val profileImage = MutableLiveData(Uri.EMPTY)

    fun requestProfile() {
        Timber.tag(TAG).d("requestProfile: ")
        getMasterUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("getMasterSuccessfully: $it")
                    _profile.value = Profile.fromMasterDto(it)
                },
                onError = {
                    Timber.tag(TAG).d("getMasterFailed: $it")
                    setAction(GET_PROFILE_FAILED)
                }
            )
            .addToDisposable()
    }

    fun saveMasterProfileImage() {
        Timber.tag(TAG).d("saveMasterProfile: ")
        saveMasterUseCase(
            masterDto = MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
            ),
            profileImageUri = profileImage.value,
        )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(
            onSuccess = {
                Timber.tag(TAG).d("saveMasterProfile successfully: $it")
//                _profile.value = Profile.fromMasterDto(it)
            },
            onError = {
                Timber.tag(TAG).d("saveMasterProfile failed: $it")
                setAction(REQUEST_FAILED)
            }
        ).addToDisposable()
    }

    companion object {
        private const val TAG = "ProfileViewModel"
        const val GET_PROFILE_FAILED = "GET_PROFILE_FAILED"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}