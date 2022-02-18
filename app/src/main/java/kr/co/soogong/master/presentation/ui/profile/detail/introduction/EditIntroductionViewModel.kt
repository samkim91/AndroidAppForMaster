package kr.co.soogong.master.presentation.ui.profile.detail.introduction

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileViewModel
import javax.inject.Inject

@HiltViewModel
class EditIntroductionViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileViewModel(getProfileUseCase, saveMasterUseCase) {

    val introduction = MutableLiveData("")

    init {
        requestIntroduction()
    }

    private fun requestIntroduction() {
        requestProfile {
            introduction.postValue(it.requiredInformation.introduction)
        }
    }

    fun saveIntroduction() {
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