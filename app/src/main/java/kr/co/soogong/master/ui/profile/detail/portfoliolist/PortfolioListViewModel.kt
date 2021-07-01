package kr.co.soogong.master.ui.profile.detail.portfoliolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.GetPortfolioListUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePortfolioUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.data.model.profile.PortfolioCodeTable
import kr.co.soogong.master.data.model.profile.PriceByProjectCodeTable
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PORTFOLIO
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PortfolioListViewModel @Inject constructor(
    private val getPortfolioListUseCase: GetPortfolioListUseCase,
    private val savePortfolioUseCase: SavePortfolioUseCase,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val pageName = PortfolioListActivityHelper.getPageNameFromSavedState(savedStateHandle)

    private val _itemList = MutableLiveData<List<PortfolioDto>>()
    val itemList: LiveData<List<PortfolioDto>>
        get() = _itemList

    fun requestPortfolioList() {
        val type = when (pageName) {
            PORTFOLIO -> PortfolioCodeTable.code
            else -> PriceByProjectCodeTable.code
        }
        Timber.tag(TAG).d("requestPortfolioList: $type")

        getPortfolioListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestPortfolioList successfully: $it")
                    _itemList.postValue(it.filter { portfolioDto -> portfolioDto.type == type })
                },
                onError = {
                    Timber.tag(TAG).d("requestPortfolioList failed: $it")
                    setAction(REQUEST_FAILED)
                },
            ).addToDisposable()
    }

    fun deletePortfolio(itemId: Int) {
        Timber.tag(TAG).d("deletePortfolio: $itemId")
        savePortfolioUseCase(
            PortfolioDto(
                id = itemId,
                masterId = getMasterIdFromSharedUseCase(),
                title = null,
                type = null,
                useYn = false
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("deletePortfolio successfully: $it")
                    requestPortfolioList()
                },
                onError = {
                    Timber.tag(TAG).d("deletePortfolio failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ProfileViewModel"
        const val REQUEST_FAILED = "DELETE_ITEM_FAILED"
    }
}