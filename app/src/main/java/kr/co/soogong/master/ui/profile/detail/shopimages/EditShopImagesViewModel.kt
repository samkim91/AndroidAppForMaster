package kr.co.soogong.master.ui.profile.detail.shopimages

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.global.CodeTable
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditShopImagesViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val shopImages = ListLiveData<AttachmentDto>()

    fun requestShopImages() {
        Timber.tag(TAG).d("requestShopImages: ")

        requestProfile {
            profile.value = it
            it.requiredInformation?.shopImages?.let { list ->
                shopImages.addAll(list)
            }
        }
    }

    // TODO: 2021/12/15 BaseViewModel 의 show/dismiss loading 을 사용하도록 변경해야함
    fun saveShopImages() {
        Timber.tag(TAG).d("saveShopImages: ")
        shopImages.value?.filter { attachmentDto ->
            attachmentDto.id == null        // 이전에 저장되지 않았던 이미지들만 업로드 해야하기 때문에, attachmentId가 null 인것들만 필터
        }?.let { list ->
            saveMaster(
                MasterDto(
                    id = profile.value?.id,
                    uid = profile.value?.uid,
                    shopImages = shopImages.value,
                    updateShopImagesYn = true,
                    approvedStatus = if (profile.value?.approvedStatus == CodeTable.APPROVED.code) CodeTable.REQUEST_APPROVE.code else null,
                ),
                shopImagesUris = list.map {
                    it.uri!!
                }
            )
        }
    }

    companion object {
        private const val TAG = "EditShopImagesViewModel"
    }
}