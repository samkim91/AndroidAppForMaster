package kr.co.soogong.master.presentation.ui.profile.detail.major

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.entity.common.major.ProjectDto
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.common.major.Project
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileViewModel
import kr.co.soogong.master.utility.ListLiveData
import javax.inject.Inject

@HiltViewModel
class EditMajorViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileViewModel(getProfileUseCase, saveMasterUseCase) {

    val projects = ListLiveData<Project>()

    init {
        requestMajor()
    }

    private fun requestMajor() {
        requestProfile {
            profile.value = it
            it.requiredInformation.projects?.let { list ->
                projects.addAll(list)
            }
        }
    }

    fun saveMajor() {
        projects.value?.let {
            saveMaster(
                MasterDto(
                    id = profile.value?.id,
                    uid = profile.value?.uid,
                    projectDtos = ProjectDto.fromProjects(projects.value),
                    approvedStatus = if (profile.value?.approvedStatus == CodeTable.APPROVED) CodeTable.REQUEST_APPROVE.code else null,
                )
            )
        }
    }

    companion object {
        private const val TAG = "EditMajorViewModel"
    }
}