package kr.co.soogong.master.ui.profile.edit.requiredinformation.representativeimages

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.profile.GetRepresentativeImagesUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveRepresentativeImagesUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditRepresentativeImagesViewModel @Inject constructor(
    private val getRepresentativeImagesUseCase: GetRepresentativeImagesUseCase,
    private val saveRepresentativeImagesUseCase: SaveRepresentativeImagesUseCase
) : BaseViewModel() {
    val representativeImages = ListLiveData<Uri>()

    fun requestRepresentativeImages() {
        Timber.tag(TAG).d("requestRepresentativeImages: ")
        getRepresentativeImagesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { representativeImages.addAll(it) },
                onError = { setAction(GET_REPRESENTATIVE_IMAGES_FAILED) }
            ).addToDisposable()
    }

    fun saveRepresentativeImg() {
        Timber.tag(TAG).d("saveRepresentativeImg: ")
        representativeImages.value?.let {
            saveRepresentativeImagesUseCase(
                it
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_REPRESENTATIVE_IMAGES_SUCCESSFULLY) },
                    onError = { setAction(SAVE_REPRESENTATIVE_IMAGES_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditRepresentativeImagesViewModel"
        const val SAVE_REPRESENTATIVE_IMAGES_SUCCESSFULLY =
            "SAVE_REPRESENTATIVE_IMAGES_SUCCESSFULLY"
        const val SAVE_REPRESENTATIVE_IMAGES_FAILED = "SAVE_REPRESENTATIVE_IMAGES_FAILED"
        const val GET_REPRESENTATIVE_IMAGES_FAILED = "GET_REPRESENTATIVE_IMAGES_FAILED"
    }
}