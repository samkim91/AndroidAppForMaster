package kr.co.soogong.master.ui.profile.detail.requiredinformation.shopimages

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditShopImagesViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()
    val shopImages = ListLiveData<AttachmentDto>()

    fun requestShopImages() {
        Timber.tag(TAG).d("requestShopImages: ")
        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    Timber.tag(TAG).d("requestThumbnails successfully: $profile")
                    _profile.value = profile
                    profile.requiredInformation?.shopImages?.let {
                        shopImages.addAll(it)
                    }
                },
                onError = {
                    Timber.tag(TAG).d("requestShopImages failed: $it")
                    setAction(GET_SHOP_IMAGES_FAILED)
                }
            ).addToDisposable()
    }

    fun saveShopImages() {
        Timber.tag(TAG).d("saveShopImages: ")
        shopImages.value?.filter { attachmentDto ->
            attachmentDto.id == null
        }?.let { list ->
            saveMasterUseCase(
                MasterDto(
                    id = _profile.value?.id,
                    uid = _profile.value?.uid,
                    shopImages = shopImages.value,
                    updateShopImagesYn = true,
                ),
                shopImagesUris = list.map {
                    it.uri!!
                }
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("saveShopImages successfully: $it")
                        _profile.value = Profile.fromMasterDto(it)
                        setAction(SAVE_SHOP_IMAGES_SUCCESSFULLY)
                    },
                    onError = {
                        Timber.tag(TAG).d("saveShopImages failed: $it")
                        setAction(SAVE_SHOP_IMAGES_FAILED)
                    }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditShopImagesViewModel"
        const val SAVE_SHOP_IMAGES_SUCCESSFULLY =
            "SAVE_SHOP_IMAGES_SUCCESSFULLY"
        const val SAVE_SHOP_IMAGES_FAILED = "SAVE_SHOP_IMAGES_FAILED"
        const val GET_SHOP_IMAGES_FAILED = "GET_SHOP_IMAGES_FAILED"
    }
}