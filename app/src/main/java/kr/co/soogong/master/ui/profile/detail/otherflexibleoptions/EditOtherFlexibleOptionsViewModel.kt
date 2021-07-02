package kr.co.soogong.master.ui.profile.detail.otherflexibleoptions

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditOtherFlexibleOptionsViewModel @Inject constructor(
    private val saveMasterUseCase: SaveMasterUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()
    val otherFlexibleOptions = MutableLiveData<List<MasterConfigDto>>()

    fun requestOtherFlexibleOptions() {
        Timber.tag(TAG).d("requestOtherFlexibleOptions: ")

        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    Timber.tag(TAG).d("requestOtherFlexibleOptions successfully: $profile")
                    _profile.value = profile
                    profile.basicInformation?.otherFlexibleOptions?.let {
                        otherFlexibleOptions.postValue(it)
                    }
                },
                onError = { setAction(GET_OTHER_FLEXIBLE_OPTIONS_FAILED) }
            ).addToDisposable()
    }

    fun saveOtherFlexibleOptions() {
        Timber.tag(TAG).d("saveOtherFlexibleOptions: ${otherFlexibleOptions.value}")

//        saveMasterUseCase(
//            MasterDto(
//                id = _profile.value?.id,
//                uid = _profile.value?.uid,
//
//                ).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeBy(
//                    onSuccess = {
//                        Timber.tag(TAG).d("saveOtherFlexibleOptions successfully: $it")
//                        setAction(SAVE_OTHER_FLEXIBLE_OPTIONS_SUCCESSFULLY)
//                    },
//                    onError = {
//                        Timber.tag(TAG).d("saveOtherFlexibleOptions successfully: $it")
//                        setAction(SAVE_OTHER_FLEXIBLE_OPTIONS_FAILED)
//                    }
//                ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditOtherFlexibleOptionsViewModel"

        const val SAVE_OTHER_FLEXIBLE_OPTIONS_SUCCESSFULLY =
            "SAVE_OTHER_FLEXIBLE_OPTIONS_SUCCESSFULLY"
        const val SAVE_OTHER_FLEXIBLE_OPTIONS_FAILED = "SAVE_OTHER_FLEXIBLE_OPTIONS"
        const val GET_OTHER_FLEXIBLE_OPTIONS_FAILED = "GET_OTHER_FLEXIBLE_OPTIONS_FAILED"
    }
}