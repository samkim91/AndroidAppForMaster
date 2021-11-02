package kr.co.soogong.master.ui.profile.detail.requiredinformation.ownername

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.ApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditOwnerNameViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val ownerName = MutableLiveData("")

    fun requestOwnerName() {
        Timber.tag(TAG).d("requestOwnerName: ")

        requestProfile {
            profile.value = it
            ownerName.postValue(it.requiredInformation?.ownerName)
        }
    }

    fun saveOwnerName() {
        Timber.tag(TAG).d("saveOwnerName: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                ownerName = ownerName.value,
                approvedStatus = if (profile.value?.approvedStatus == ApprovedCodeTable.code) RequestApproveCodeTable.code else null,
            )
        )
    }

    companion object {
        private const val TAG = "EditOwnerNameViewModel"
    }
}