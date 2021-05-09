package kr.co.soogong.master.ui.profile.edit.flexiblecost

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.profile.PriceByProject
import kr.co.soogong.master.domain.usecase.*
import kr.co.soogong.master.domain.usecase.profile.GetPriceByProjectUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePriceByProjectUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class EditFlexibleCostViewModel @Inject constructor(
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
    private val savePriceByProjectUseCase: SavePriceByProjectUseCase,
    private val getPriceByProjectUseCase: GetPriceByProjectUseCase
) : BaseViewModel() {
    val travelCost = MutableLiveData(0)
    val craneUsage = MutableLiveData(0)
    val packageCost = MutableLiveData(0)
    val otherCostInformation = MutableLiveData("")

    fun getFlexibleCosts() {

    }

    fun saveFlexibleCosts() {
        savePriceByProjectUseCase(
            PriceByProject.NULL_PRICE_BY_PROJECT
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {},
                onError = {}
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditPortfolioViewModel"

    }
}