package kr.co.soogong.master.ui.profile.detail.portfoliolist

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.domain.usecase.profile.DeletePortfolioUseCase
import kr.co.soogong.master.domain.usecase.profile.DeletePriceByProjectUseCase
import kr.co.soogong.master.domain.usecase.profile.GetPortfolioListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PortfolioListViewModel @Inject constructor(
    private val getPortfolioListUseCase: GetPortfolioListUseCase,
    private val deletePortfolioUseCase: DeletePortfolioUseCase,
    private val deletePriceByProjectUseCase: DeletePriceByProjectUseCase,
) : BaseViewModel() {
    val itemList = ListLiveData<PortfolioDto>()

    fun requestPortfolioList(type: String) {
        Timber.tag(TAG).d("requestPortfolioList: ")
        getPortfolioListUseCase(type = type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { itemList.addAll(it) },
                onError = { setAction(GET_ITEMS_FAILED) }
            ).addToDisposable()
    }

    fun deletePortfolio(itemId: Int) {
        Timber.tag(TAG).d("deletePortfolio: $itemId")
        deletePortfolioUseCase(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { itemList.removeAt(itemId) },
                onError = { setAction(DELETE_ITEM_FAILED) }
            ).addToDisposable()
    }

    fun deletePriceByProject(itemId: Int) {
        Timber.tag(TAG).d("deletePriceByProject: $itemId")
        deletePriceByProjectUseCase(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { itemList.removeAt(itemId) },
                onError = { setAction(DELETE_ITEM_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ProfileViewModel"

        const val GET_ITEMS_FAILED = "GET_ITEMS_FAILED"
        const val DELETE_ITEM_FAILED = "DELETE_ITEM_FAILED"
    }
}