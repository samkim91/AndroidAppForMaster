package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.profile.PortfolioDto
import kr.co.soogong.master.domain.repository.ProfileRepository
import kr.co.soogong.master.presentation.ui.common.EndlessScrollableViewModel
import kr.co.soogong.master.presentation.uihelper.profile.PortfolioListActivityHelper
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PortfolioListViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    val savedStateHandle: SavedStateHandle,
) : EndlessScrollableViewModel() {
    val type = PortfolioListActivityHelper.getTypeFromSavedState(savedStateHandle)

    val items = ListLiveData<PortfolioDto>()

    override fun initList() {
        Timber.tag(TAG).d("initList: ")
        items.clear()
        resetState()
        requestPortfolios()
    }

    private fun requestPortfolios() {
        Timber.tag(TAG).d("requestPortfolioList: $type")

        profileRepository.getPortfolios(type = type.code, offset = offset, pageSize = pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { pageableContentDto ->
                    Timber.tag(TAG).d("requestPortfolioList successfully: ")
                    last = pageableContentDto.last
                    totalItemCount += pageableContentDto.numberOfElements
                    items.addAll(pageableContentDto.content)
                },
                onError = {
                    Timber.tag(TAG).d("requestPortfolioList failed: $it")
                    setAction(REQUEST_FAILED)
                },
            ).addToDisposable()
    }

    fun deletePortfolio(itemId: Int) {
        Timber.tag(TAG).d("deletePortfolio: $itemId")

        profileRepository.deletePortfolio(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("deletePortfolio successfully: ")
                    initList()
                },
                onError = {
                    Timber.tag(TAG).d("deletePortfolio failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}