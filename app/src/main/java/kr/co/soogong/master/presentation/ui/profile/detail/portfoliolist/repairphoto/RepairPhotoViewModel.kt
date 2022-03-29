package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.repairphoto

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.data.entity.profile.portfolio.SaveRepairPhotoDto
import kr.co.soogong.master.domain.entity.common.major.Project
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.portfolio.SaveRepairPhotoUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RepairPhotoViewModel @Inject constructor(
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val saveRepairPhotoUseCase: SaveRepairPhotoUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    val maxPhoto: Int = 5
    val maxProject: Int = 1

    val portfolio = RepairPhotoFragment.getRepairPhoto(savedStateHandle)

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val project = MutableLiveData(Project.defaultData)
    val repairPhotos = ListLiveData<AttachmentDto>()

    init {
        setInitialRepairPhoto()
    }

    private fun setInitialRepairPhoto() {
        Timber.tag(TAG).d("setInitialRepairPhoto: $portfolio")

        portfolio.value?.let { portfolio ->
            title.postValue(portfolio.title)
            portfolio.images?.let { repairPhotos.addAll(portfolio.images) }
            description.postValue(portfolio.description)
            project.postValue(portfolio.project)
        }
    }

    fun saveRepairPhoto() {
        Timber.tag(TAG).d("saveRepairPhoto: ")
        viewModelScope.launch {
            try {
                setAction(SHOW_LOADING)
                saveRepairPhotoUseCase(
                    SaveRepairPhotoDto(
                        id = portfolio.value?.id,
                        masterId = getMasterIdFromSharedUseCase(),
                        projectId = project.value?.id!!,
                        title = title.value!!,
                        description = description.value ?: "",
                        images = repairPhotos.value,
                        updateImages = true
                    ),
                    newImages = repairPhotos.value?.map { attachmentDto ->
                        attachmentDto.uri!!
                    }
                )

                Timber.tag(TAG).d("savePortfolio successfully: ")
                setAction(DISMISS_LOADING)
                setAction(SAVE_PORTFOLIO_SUCCESSFULLY)
            } catch (e: Exception) {
                Timber.tag(TAG).d("savePortfolio failed: $e")
                setAction(REQUEST_FAILED)
            }
        }
    }

    fun startRepairPhotosPicker() {
        Timber.tag(TAG).d("startImagePickerForBefore: ")
        setAction(START_REPAIR_PHOTOS_PICKER)
    }

    companion object {
        private val TAG = RepairPhotoViewModel::class.java.simpleName
        const val SAVE_PORTFOLIO_SUCCESSFULLY = "SAVE_PORTFOLIO_SUCCESSFULLY"
        const val START_REPAIR_PHOTOS_PICKER = "START_REPAIR_PHOTOS_PICKER"
    }
}