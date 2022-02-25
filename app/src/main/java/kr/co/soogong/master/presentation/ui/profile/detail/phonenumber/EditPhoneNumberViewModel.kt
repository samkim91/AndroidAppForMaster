package kr.co.soogong.master.presentation.ui.profile.detail.phonenumber

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileViewModel
import kr.co.soogong.master.utility.PhoneNumberHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditPhoneNumberViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileViewModel(getProfileUseCase, saveMasterUseCase) {

    init {
        requestProfile()
    }

    private fun requestProfile() {
        requestProfile {
            profile.value = it
        }
    }

    fun savePhoneNumber(tel: String) {
        Timber.tag(TAG).d("savePhoneNumber: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                tel = PhoneNumberHelper.toGlobalNumber(tel),
            )
        )
    }

    companion object {
        private const val TAG = "EditPhoneNumberViewModel"

    }
}