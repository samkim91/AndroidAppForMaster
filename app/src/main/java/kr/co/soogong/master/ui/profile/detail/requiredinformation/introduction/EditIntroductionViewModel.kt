package kr.co.soogong.master.ui.profile.detail.requiredinformation.introduction

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditIntroductionViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val introduction = MutableLiveData("")

    fun requestIntroduction() {
        Timber.tag(TAG).d("requestIntroduction: ")

        requestProfile {
            profile.value = it
            introduction.postValue(it.requiredInformation?.introduction)
        }
    }

    fun saveIntroduction() {
        Timber.tag(TAG).d("saveIntroduction: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                introduction = introduction.value
            )
        )
    }

    companion object {
        private const val TAG = "EditIntroductionViewModel"
    }
}