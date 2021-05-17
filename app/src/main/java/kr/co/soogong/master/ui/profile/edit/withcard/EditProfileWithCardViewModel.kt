package kr.co.soogong.master.ui.profile.edit.withcard

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.profile.IEditProfileWithCard
import kr.co.soogong.master.domain.usecase.profile.*
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

    fun getPortfolioList() {
        Timber.tag(TAG).d("getPortfolioList: ")
        viewModelScope.launch {
            itemList.addAll(getPortfolioListUseCase())
        }
    }

    fun getPriceByProjectList() {
        Timber.tag(TAG).d("getPriceByProjectList: ")
        viewModelScope.launch {
            itemList.addAll(getPriceByProjectListUseCase())
        }
    }

    fun deletePortfolio(itemId: Int) {
        Timber.tag(TAG).d("deletePortfolio: $itemId")
        deletePortfolioUseCase(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {

                },
                onError = {

                }
            ).addToDisposable()
    }

    fun deletePriceByProject(itemId: Int) {
        Timber.tag(TAG).d("deletePriceByProject: $itemId")
        deletePriceByProjectUseCase(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {

                },
                onError = {

                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}