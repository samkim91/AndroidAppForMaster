package kr.co.soogong.master.ui.profile.detail.requiredinformation.ownername

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditOwnerNameViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()

    val ownerName = MutableLiveData("")

    fun requestOwnerName() {
        Timber.tag(TAG).d("requestOwnerName: ")

        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    Timber.tag(TAG).d("requestOwnerName successfully: $profile")
                    _profile.value = profile
                    ownerName.postValue(profile.requiredInformation?.ownerName)
                },
                onError = {
                    Timber.tag(TAG).d("requestOwnerName failed: $it")
                    setAction(GET_OWNER_NAME_FAILED)
                }
            ).addToDisposable()
    }

    fun saveOwnerName() {
        Timber.tag(TAG).d("saveOwnerName: ")
        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                ownerName = ownerName.value,
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("saveOwnerName successfully: $it")
                    setAction(SAVE_OWNER_NAME_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("saveOwnerName failed: $it")
                    setAction(SAVE_OWNER_NAME_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditOwnerNameViewModel"
        const val SAVE_OWNER_NAME_SUCCESSFULLY = "SAVE_OWNER_NAME_SUCCESSFULLY"
        const val SAVE_OWNER_NAME_FAILED = "SAVE_OWNER_NAME_FAILED"
        const val GET_OWNER_NAME_FAILED = "GET_OWNER_NAME_FAILED"
    }
}