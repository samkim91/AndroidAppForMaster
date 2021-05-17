package kr.co.soogong.master.ui.profile.edit.requiredinformation.representativeimages

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.usecase.profile.GetRepresentativeImagesUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveRepresentativeImagesUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.utils.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditRepresentativeImagesViewModel @Inject constructor(
    private val getRepresentativeImagesUseCase: GetRepresentativeImagesUseCase,
    private val saveRepresentativeImagesUseCase: SaveRepresentativeImagesUseCase
) : BaseViewModel() {
    val representativeImages = ListLiveData<Uri>()
    
    fun getRepresentativeImg() {
        Timber.tag(TAG).d("getRepresentativeImg: ")
        viewModelScope.launch {
            representativeImages.addAll(getRepresentativeImagesUseCase())
        }
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
        const val SAVE_REPRESENTATIVE_IMAGES_SUCCESSFULLY = "SAVE_REPRESENTATIVE_IMAGES_SUCCESSFULLY"
        const val SAVE_REPRESENTATIVE_IMAGES_FAILED = "SAVE_REPRESENTATIVE_IMAGES_FAILED"

    }
}