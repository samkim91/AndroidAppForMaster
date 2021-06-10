package kr.co.soogong.master.ui.profile.edit.withcard

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.profile.IEditProfileWithCard
import kr.co.soogong.master.domain.usecase.profile.DeletePortfolioUseCase
import kr.co.soogong.master.domain.usecase.profile.DeletePriceByProjectUseCase
import kr.co.soogong.master.domain.usecase.profile.GetPortfolioListUseCase
import kr.co.soogong.master.domain.usecase.profile.GetPriceByProjectListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.utils.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditProfileWithCardViewModel @Inject constructor(
    private val getPortfolioListUseCase: GetPortfolioListUseCase,
    private val getPriceByProjectListUseCase: GetPriceByProjectListUseCase,
    private val deletePortfolioUseCase: DeletePortfolioUseCase,
    private val deletePriceByProjectUseCase: DeletePriceByProjectUseCase,
) : BaseViewModel() {
    val itemList = ListLiveData<IEditProfileWithCard>()

    fun requestPortfolioList() {
        Timber.tag(TAG).d("requestPortfolioList: ")
        getPortfolioListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { itemList.addAll(it) },
                onError = { setAction(GET_ITEMS_FAILED) }
            ).addToDisposable()
    }

    fun requestPriceByProjectList() {
        Timber.tag(TAG).d("requestPriceByProjectList: ")
        getPriceByProjectListUseCase()
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