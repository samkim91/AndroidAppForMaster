package kr.co.soogong.master.ui.profile.edit.flexiblecost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.profile.FlexibleCost
import kr.co.soogong.master.data.profile.PriceByProject
import kr.co.soogong.master.domain.usecase.*
import kr.co.soogong.master.domain.usecase.profile.GetFlexibleCostUseCase
import kr.co.soogong.master.domain.usecase.profile.GetPriceByProjectUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveFlexibleCostUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePriceByProjectUseCase
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

    fun getFlexibleCosts() {
        Timber.tag(TAG).d("getFlexibleCosts: ")
        viewModelScope.launch {
            getFlexibleCostUseCase().let { flexibleCost ->
                travelCost.postValue(flexibleCost.travelCost)
                craneUsage.postValue(flexibleCost.craneUsage)
                packageCost.postValue(flexibleCost.packageCost)
                otherCostInformation.postValue(flexibleCost.otherCostInformation)
            }
        }
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
                onSuccess = {},
                onError = {}
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditFlexibleCostViewModel"

    }
}