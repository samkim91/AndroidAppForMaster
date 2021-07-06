package kr.co.soogong.master.ui.profile.detail.email

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditEmailViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()

    val localPart = MutableLiveData("")
    val domain = MutableLiveData("")

    fun requestEmail() {
        Timber.tag(TAG).d("requestEmail: ")

        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    Timber.tag(TAG).d("requestEmail successfully: $profile")

                    _profile.postValue(profile)
                    profile.basicInformation?.email?.let {
                        if (it.contains("@")) {
                            localPart.postValue(it.split("@")[0])
                            domain.postValue(it.split("@")[1])
                        } else {
                            localPart.postValue(it)
                        }
                    }
                },
                onError = {
                    Timber.tag(TAG).d("requestEmail failed: $it")
                    setAction(GET_EMAIL_FAILED)
                }
            ).addToDisposable()
    }

    fun saveEmailInfo() {
        Timber.tag(TAG).d("saveEmailInfo: ")

        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                email = "${localPart.value}@${domain.value}",
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_EMAIL_SUCCESSFULLY) },
                onError = { setAction(SAVE_EMAIL_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditEmailViewModel"
        const val SAVE_EMAIL_SUCCESSFULLY = "SAVE_EMAIL_SUCCESSFULLY"
        const val SAVE_EMAIL_FAILED = "SAVE_EMAIL_FAILED"
        const val GET_EMAIL_FAILED = "GET_EMAIL_FAILED"
    }
}