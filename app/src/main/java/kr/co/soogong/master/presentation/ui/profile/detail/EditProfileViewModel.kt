package kr.co.soogong.master.presentation.ui.profile.detail

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.entity.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber

open class EditProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {

    val profile = MutableLiveData<Profile>()

    fun requestProfile(onSuccess: (Profile) -> Unit) {
        Timber.tag(TAG).d("requestProfile: ")
        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    profile.value = it
                    onSuccess(it)
                },
                onError = {
                    Timber.tag(TAG).d("requestProfile failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveMaster(
        masterDto: MasterDto,
        profileImageUri: Uri? = null,
        businessRegistImageUri: Uri? = null,
        shopImagesUris: List<Uri>? = null,
    ) {
        Timber.tag(TAG).d("saveMaster: $masterDto")
        saveMasterUseCase(
            masterDto,
            profileImageUri,
            businessRegistImageUri,
            shopImagesUris
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("saveMaster successful: $it")
                    setAction(REQUEST_SUCCESS)
                },
                onError = {
                    Timber.tag(TAG).d("saveMaster failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditProfileOpenViewModel"
    }
}