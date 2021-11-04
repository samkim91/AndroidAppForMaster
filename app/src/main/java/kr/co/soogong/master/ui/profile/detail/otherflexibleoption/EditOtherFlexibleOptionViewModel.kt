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
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditOtherFlexibleOptionViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val otherFlexibleOption = MutableLiveData<List<MasterConfigDto>>()

    fun requestOtherFlexibleOption() {
        Timber.tag(TAG).d("requestOtherFlexibleOption: ")

        requestProfile {
            profile.value = it
            it.basicInformation?.otherFlexibleOption?.let { configs ->
                otherFlexibleOption.postValue(configs)
            }
        }
    }

    fun saveOtherFlexibleOption() {
        Timber.tag(TAG).d("saveOtherFlexibleOption: ${otherFlexibleOption.value}")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                masterConfigs = otherFlexibleOption.value,
            )
        )
    }

    companion object {
        private const val TAG = "EditOtherFlexibleOptionViewModel"
    }
}