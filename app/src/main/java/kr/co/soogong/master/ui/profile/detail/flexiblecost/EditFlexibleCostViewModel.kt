package kr.co.soogong.master.ui.profile.detail.flexiblecost

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.*
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

    val flexibleCost = MutableLiveData<List<MasterConfigDto>>()

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
                    profile.basicInformation?.flexibleCost?.let { masterConfigList ->
                        flexibleCost.postValue(masterConfigList)

                        masterConfigList.find { masterConfigDto -> masterConfigDto.code == TravelCostCodeTable.code }?.value?.let {
                            travelCostValue.postValue(it)
                        }
                        masterConfigList.find { masterConfigDto -> masterConfigDto.code == CraneUsageCodeTable.code }?.value?.let {
                            craneUsageValue.postValue(it)
                        }
                        masterConfigList.find { masterConfigDto -> masterConfigDto.code == PackageCostCodeTable.code }?.value?.let {
                            packageCostValue.postValue(it)
                        }
                        masterConfigList.find { masterConfigDto -> masterConfigDto.code == OtherInfoCodeTable.code }?.value?.let {
                            otherCostInformation.postValue(it)
                        }
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
                masterConfigs = flexibleCost.value?.map { masterConfig ->
                    MasterConfigDto(
                        id = masterConfig.id,
                        groupCode = FlexibleCostCodeTable.code,
                        code = masterConfig.code,
                        name = masterConfig.name,
                        value = when (masterConfig.code) {
                            TravelCostCodeTable.code -> travelCostValue.value
                            CraneUsageCodeTable.code -> craneUsageValue.value
                            PackageCostCodeTable.code -> packageCostValue.value
                            else -> otherCostInformation.value
                        }
                    )
                }
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