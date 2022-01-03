package kr.co.soogong.master.ui.profile.detail.major

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.dto.major.ProjectDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.major.Project
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

    val projects = ListLiveData<Project>()

    fun requestMajor() {
        Timber.tag(TAG).d("requestMajor: ")

        requestProfile {
            profile.value = it
            it.requiredInformation?.projects?.let { list ->
                projects.addAll(list)
            }
        }
    }

    fun saveMajor() {
        Timber.tag(TAG).d("saveMajor: ")
        projects.value?.let {
            saveMaster(
                MasterDto(
                    id = profile.value?.id,
                    uid = profile.value?.uid,
                    projectDtos = ProjectDto.fromProjects(projects.value),
                    approvedStatus = if (profile.value?.approvedStatus == CodeTable.APPROVED.code) CodeTable.REQUEST_APPROVE.code else null,
                )
            )
        }
    }

    companion object {
        private const val TAG = "EditMajorViewModel"
    }
}