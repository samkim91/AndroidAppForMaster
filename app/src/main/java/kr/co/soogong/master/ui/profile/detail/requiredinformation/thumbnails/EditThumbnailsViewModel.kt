package kr.co.soogong.master.ui.profile.detail.requiredinformation.thumbnails

import android.net.Uri
import androidx.core.net.toUri
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveThumbnailsUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditThumbnailsViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveThumbnailsUseCase: SaveThumbnailsUseCase
) : BaseViewModel() {
    val thumbnails = ListLiveData<Uri>()

    fun requestThumbnails() {
        Timber.tag(TAG).d("requestThumbnails: ")
        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    Timber.tag(TAG).d("requestThumbnails successfully: $profile")
                    profile.requiredInformation?.representativeImages?.map { attachmentDto ->
                        attachmentDto.url?.toUri()!!
                    }?.let {
                        thumbnails.addAll(it)
                    }
                },
                onError = {
                    setAction(GET_THUMBNAILS_FAILED)
                }
            ).addToDisposable()
    }

    fun saveThumbnails() {
        Timber.tag(TAG).d("saveThumbnails: ")
        thumbnails.value?.let {
            saveThumbnailsUseCase(
                it
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_THUMBNAILS_SUCCESSFULLY) },
                    onError = { setAction(SAVE_THUMBNAILS_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditThumbnailsViewModel"
        const val SAVE_THUMBNAILS_SUCCESSFULLY =
            "SAVE_THUMBNAILS_SUCCESSFULLY"
        const val SAVE_THUMBNAILS_FAILED = "SAVE_THUMBNAILS_FAILED"
        const val GET_THUMBNAILS_FAILED = "GET_THUMBNAILS_FAILED"
    }
}