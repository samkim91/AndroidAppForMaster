package kr.co.soogong.master.ui.profile.detail.flexiblecost

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.profile.FlexibleCost
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveFlexibleCostUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditFlexibleCostViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveFlexibleCostUseCase: SaveFlexibleCostUseCase,
) : BaseViewModel() {
    val travelCost = MutableLiveData("")
    val craneUsage = MutableLiveData("")
    val packageCost = MutableLiveData("")
    val otherCostInformation = MutableLiveData("")

    fun requestFlexibleCosts() {
        Timber.tag(TAG).d("requestFlexibleCosts: ")

        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    Timber.tag(TAG).d("requestFlexibleCosts Successfully: $profile")
                    travelCost.postValue(profile.basicInformation?.flexibleCost?.travelCostValue)
                    craneUsage.postValue(profile.basicInformation?.flexibleCost?.craneUsageValue)
                    packageCost.postValue(profile.basicInformation?.flexibleCost?.packageCostValue)
                    otherCostInformation.postValue(profile.basicInformation?.flexibleCost?.otherCostInformation)
                },
                onError = {
                    Timber.tag(TAG).d("requestFlexibleCosts Failed: $it")
                    setAction(GET_FLEXIBLE_COST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveFlexibleCosts() {
        Timber.tag(TAG).d("saveFlexibleCosts: ")
        saveFlexibleCostUseCase(
            FlexibleCost(
                travelCost = travelCost.value,
                craneUsage = craneUsage.value,
                packageCost = packageCost.value,
                otherCostInformation = otherCostInformation.value
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_FLEXIBLE_COST_SUCCESSFULLY) },
                onError = { setAction(SAVE_FLEXIBLE_COST_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditFlexibleCostViewModel"
        const val SAVE_FLEXIBLE_COST_SUCCESSFULLY = "SAVE_FLEXIBLE_COST_SUCCESSFULLY"
        const val SAVE_FLEXIBLE_COST_FAILED = "SAVE_FLEXIBLE_COST_FAILED"
        const val GET_FLEXIBLE_COST_FAILED = "GET_FLEXIBLE_COST_FAILED"
    }
}