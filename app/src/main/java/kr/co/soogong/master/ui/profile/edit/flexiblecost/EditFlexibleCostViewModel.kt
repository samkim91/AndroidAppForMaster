package kr.co.soogong.master.ui.profile.edit.flexiblecost

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.profile.FlexibleCost
import kr.co.soogong.master.domain.usecase.profile.GetFlexibleCostUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveFlexibleCostUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditFlexibleCostViewModel @Inject constructor(
    private val saveFlexibleCostUseCase: SaveFlexibleCostUseCase,
    private val getFlexibleCostUseCase: GetFlexibleCostUseCase
) : BaseViewModel() {
    val travelCost = MutableLiveData("")
    val craneUsage = MutableLiveData("")
    val packageCost = MutableLiveData("")
    val otherCostInformation = MutableLiveData("")

    fun requestFlexibleCosts() {
        Timber.tag(TAG).d("requestFlexibleCosts: ")

        getFlexibleCostUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { flexibleCost ->
                    travelCost.postValue(flexibleCost.travelCost)
                    craneUsage.postValue(flexibleCost.craneUsage)
                    packageCost.postValue(flexibleCost.packageCost)
                    otherCostInformation.postValue(flexibleCost.otherCostInformation)
                },
                onError = { setAction(GET_FLEXIBLE_COST_FAILED) }
            ).addToDisposable()
    }

    fun saveFlexibleCosts() {
        Timber.tag(TAG).d("saveFlexibleCosts: ")
        saveFlexibleCostUseCase(
            FlexibleCost(
                travelCost = travelCost.value!!,
                craneUsage = craneUsage.value!!,
                packageCost = packageCost.value!!,
                otherCostInformation = otherCostInformation.value!!
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