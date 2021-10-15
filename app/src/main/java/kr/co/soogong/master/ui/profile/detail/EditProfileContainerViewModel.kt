package kr.co.soogong.master.ui.profile.detail

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber

// TODO: 2021/10/06 추후 EditProfile 을 하는 모든 viewModels 를 이것으로 상속시켜야한다.
open class EditProfileContainerViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {

    val profile = MutableLiveData<Profile>()

    fun requestProfile(function: (Profile) -> Unit) {
        Timber.tag(TAG).d("requestProfile: ")
        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    profile.value = it
                    function(it)
                },
                onError = {
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveMaster(masterDto: MasterDto) {
        Timber.tag(TAG).d("saveMaster: $masterDto")
        saveMasterUseCase(
            masterDto
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    setAction(SAVE_MASTER_SUCCESSFULLY)
                },
                onError = {
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditProfileContainerViewModel"
        const val SAVE_MASTER_SUCCESSFULLY = "SAVE_MASTER_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"

    }
}