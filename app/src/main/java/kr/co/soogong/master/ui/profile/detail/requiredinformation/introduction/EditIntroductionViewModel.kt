package kr.co.soogong.master.ui.profile.detail.requiredinformation.introduction

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileFromLocalUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditIntroductionViewModel @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()

    val introduction = MutableLiveData("")

    fun requestIntroduction() {
        Timber.tag(TAG).d("requestIntroduction: ")

        getProfileFromLocalUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    _profile.value = profile
                    introduction.postValue(profile.requiredInformation?.introduction)
                },
                onError = {
                    setAction(GET_INTRODUCTION_FAILED)
                }
            ).addToDisposable()
    }

    fun saveIntroduction() {
        Timber.tag(TAG).d("saveIntroduction: ")
        introduction.value?.let {
            saveMasterUseCase(
                MasterDto(
                    id = _profile.value?.id,
                    uid = _profile.value?.uid,
                    introduction = introduction.value
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_INTRODUCTION_SUCCESSFULLY) },
                    onError = { setAction(SAVE_INTRODUCTION_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditIntroductionViewModel"
        const val SAVE_INTRODUCTION_SUCCESSFULLY = "SAVE_INTRODUCTION_SUCCESSFULLY"
        const val SAVE_INTRODUCTION_FAILED = "SAVE_INTRODUCTION_FAILED"
        const val GET_INTRODUCTION_FAILED = "GET_INTRODUCTION_FAILED"

    }
}