package kr.co.soogong.master.ui.profile.detail.requiredinformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.data.model.profile.RequiredInformation
import kr.co.soogong.master.domain.usecase.auth.GetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.profile.GetProfileFromLocalUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveCareerPeriodUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditRequiredInformationViewModel @Inject constructor(
    val getMasterApprovalUseCase: GetMasterApprovalUseCase,
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _isApprovedMaster = MutableLiveData<Boolean>(getMasterApprovalUseCase())
    val isApprovedMaster: LiveData<Boolean>
        get() = _isApprovedMaster

    private val _profile = MutableLiveData<Profile>()
    val requiredInformation = MutableLiveData<RequiredInformation?>()

    fun requestRequiredInformation() {
        Timber.tag(TAG).d("requestRequiredInformation: ")
        getProfileFromLocalUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    _profile.value = profile
                    requiredInformation.postValue(profile.requiredInformation)
                },
                onError = {
                    setAction(GET_PROFILE_FAILED)
                }
            ).addToDisposable()
    }

    fun saveCareerPeriod(careerPeriod: Int) {
        Timber.tag(TAG).d("saveCareerPeriod: ")

        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                openDate = CareerConverter.toOpenDate(careerPeriod)
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_CAREER_PERIOD_SUCCESSFULLY) },
                onError = { setAction(SAVE_CAREER_PERIOD_FAILED) }
            ).addToDisposable()
    }

    fun saveServiceArea(radius: Int) {
        Timber.tag(TAG).d("saveServiceArea: ")
        // Todo.. serviceArea 저장하는 로직 추가 필요
    }

    companion object {
        private const val TAG = "EditRequiredInformationViewModel"
        const val SAVE_CAREER_PERIOD_SUCCESSFULLY = "SAVE_CAREER_PERIOD_SUCCESSFULLY"
        const val SAVE_CAREER_PERIOD_FAILED = "SAVE_CAREER_PERIOD_FAILED"
        const val GET_PROFILE_FAILED = "GET_PROFILE_FAILED"
        const val GET_CAREER_PERIOD_FAILED = "GET_CAREER_PERIOD_FAILED"
    }
}