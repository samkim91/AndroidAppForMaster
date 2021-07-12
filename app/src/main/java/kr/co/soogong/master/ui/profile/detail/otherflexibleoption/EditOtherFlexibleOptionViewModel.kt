package kr.co.soogong.master.ui.profile.detail.otherflexibleoption

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditOtherFlexibleOptionViewModel @Inject constructor(
    private val saveMasterUseCase: SaveMasterUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()
    val otherFlexibleOption = MutableLiveData<List<MasterConfigDto>>()

    fun requestOtherFlexibleOption() {
        Timber.tag(TAG).d("requestOtherFlexibleOption: ")

        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    Timber.tag(TAG).d("requestOtherFlexibleOption successfully: $profile")
                    _profile.value = profile
                    profile.basicInformation?.otherFlexibleOption?.let {
                        otherFlexibleOption.postValue(it)
                    }
                },
                onError = { setAction(GET_OTHER_FLEXIBLE_OPTION_FAILED) }
            ).addToDisposable()
    }

    fun saveOtherFlexibleOption() {
        Timber.tag(TAG).d("saveOtherFlexibleOption: ${otherFlexibleOption.value}")

        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                masterConfigs = otherFlexibleOption.value,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("saveOtherFlexibleOption successfully: $it")
                    setAction(SAVE_OTHER_FLEXIBLE_OPTION_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("saveOtherFlexibleOption successfully: $it")
                    setAction(SAVE_OTHER_FLEXIBLE_OPTION_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditOtherFlexibleOptionViewModel"

        const val SAVE_OTHER_FLEXIBLE_OPTION_SUCCESSFULLY =
            "SAVE_OTHER_FLEXIBLE_OPTION_SUCCESSFULLY"
        const val SAVE_OTHER_FLEXIBLE_OPTION_FAILED = "SAVE_OTHER_FLEXIBLE_OPTION_FAILED"
        const val GET_OTHER_FLEXIBLE_OPTION_FAILED = "GET_OTHER_FLEXIBLE_OPTION_FAILED"
    }
}