package kr.co.soogong.master.ui.profile.detail.requiredinformation.major

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MajorDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.data.model.profile.ApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditMajorViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val majors = ListLiveData<Major>()

    fun requestMajor() {
        Timber.tag(TAG).d("requestMajor: ")

        requestProfile {
            profile.value = it
            it.requiredInformation?.majors?.let { list ->
                majors.addAll(list)
            }
        }
    }

    fun saveMajor() {
        Timber.tag(TAG).d("saveMajor: ")
        majors.value?.let {
            saveMaster(
                MasterDto(
                    id = profile.value?.id,
                    uid = profile.value?.uid,
                    majors = MajorDto.fromMajorList(majors.value),
                    approvedStatus = if (profile.value?.approvedStatus == ApprovedCodeTable.code) RequestApproveCodeTable.code else null,
                )
            )
        }
    }

    companion object {
        private const val TAG = "EditMajorViewModel"
    }
}