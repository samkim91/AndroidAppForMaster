package kr.co.soogong.master.ui.profile.detail.flexiblecost

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.FlexibleCost
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditFlexibleCostViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()

    val travelCostValue = MutableLiveData<String>()
    val craneUsageValue = MutableLiveData<String>()
    val packageCostValue = MutableLiveData<String>()
    val otherCostInformation = MutableLiveData<String>()

    fun requestFlexibleCosts() {
        Timber.tag(TAG).d("requestFlexibleCosts: ")

        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    Timber.tag(TAG).d("requestFlexibleCosts Successfully: $profile")
                    _profile.postValue(profile)
                    profile.basicInformation?.flexibleCost?.travelCostValue?.let {
                        travelCostValue.postValue(
                            it
                        )
                    }
                    profile.basicInformation?.flexibleCost?.craneUsageValue?.let {
                        craneUsageValue.postValue(
                            it
                        )
                    }
                    profile.basicInformation?.flexibleCost?.packageCostValue?.let {
                        packageCostValue.postValue(
                            it
                        )
                    }
                    profile.basicInformation?.flexibleCost?.otherCostInformation?.let {
                        otherCostInformation.postValue(
                            it
                        )
                    }
                },
                onError = {
                    Timber.tag(TAG).d("requestFlexibleCosts Failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveFlexibleCosts() {
        Timber.tag(TAG).d("saveFlexibleCosts: ")
        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                masterConfigs = MasterConfigDto.fromFlexibleCost(
                    FlexibleCost(
                        travelCostId = _profile.value?.basicInformation?.flexibleCost?.travelCostId,
                        craneUsageId = _profile.value?.basicInformation?.flexibleCost?.craneUsageId,
                        packageCostId = _profile.value?.basicInformation?.flexibleCost?.packageCostId,
                        otherCostInformationId = _profile.value?.basicInformation?.flexibleCost?.otherCostInformationId,
                        travelCostValue = travelCostValue.value,
                        craneUsageValue = craneUsageValue.value,
                        packageCostValue = packageCostValue.value,
                        otherCostInformation = otherCostInformation.value,
                    )
                )
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_FLEXIBLE_COST_SUCCESSFULLY) },
                onError = { setAction(REQUEST_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditFlexibleCostViewModel"
        const val SAVE_FLEXIBLE_COST_SUCCESSFULLY = "SAVE_FLEXIBLE_COST_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}