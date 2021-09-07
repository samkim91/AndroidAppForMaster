package kr.co.soogong.master.ui.profile.detail.portfoliolist.portfolio

import android.net.Uri
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.GetPortfolioUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePortfolioUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.data.model.profile.PortfolioCodeTable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val savePortfolioUseCase: SavePortfolioUseCase,
    private val getPortfolioUseCase: GetPortfolioUseCase
) : BaseViewModel() {
    val id = MutableLiveData(-1)
    val title = MutableLiveData<String>()
    val imageBeforeJob = MutableLiveData(Uri.EMPTY)
    val imageAfterJob = MutableLiveData(Uri.EMPTY)
    val description = MutableLiveData<String>()

    fun requestPortfolio(portfolioId: Int) {
        Timber.tag(TAG).d("requestPortfolio: $portfolioId")
        getPortfolioUseCase(portfolioId, PortfolioCodeTable.code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { portfolio ->
                    portfolio.id?.let { id.postValue(it) }
                    portfolio.title?.let { title.postValue(it) }
                    portfolio.beforeImage?.url?.let { imageBeforeJob.postValue(it.toUri()) }
                    portfolio.afterImage?.url?.let { imageAfterJob.postValue(it.toUri()) }
                    portfolio.description?.let { description.postValue(it) }
                },
                onError = { setAction(GET_PORTFOLIO_FAILED) }
            ).addToDisposable()
    }

    fun savePortfolio() {
        Timber.tag(TAG).d("savePortfolio: ${id.value}")
        savePortfolioUseCase(
            portfolio = PortfolioDto(
                id = if (id.value == -1) null else id.value,
                masterId = getMasterIdFromSharedUseCase(),
                title = title.value,
                description = description.value,
                type = PortfolioCodeTable.code,
            ),
            beforeImageUri = imageBeforeJob.value,
            afterImageUri = imageAfterJob.value,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("savePortfolio successfully: $it")
                    setAction(SAVE_PORTFOLIO_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("savePortfolio failed: $it")
                    setAction(SAVE_PORTFOLIO_FAILED)
                }
            ).addToDisposable()
    }

    fun clearImageBeforeJob(v: View) {
        imageBeforeJob.value = Uri.EMPTY
    }

    fun clearImageAfterJob(v: View) {
        imageAfterJob.value = Uri.EMPTY
    }

    companion object {
        private const val TAG = "EditPortfolioViewModel"
        const val SAVE_PORTFOLIO_SUCCESSFULLY = "SAVE_PORTFOLIO_SUCCESSFULLY"
        const val SAVE_PORTFOLIO_FAILED = "SAVE_PORTFOLIO_FAILED"
        const val GET_PORTFOLIO_FAILED = "GET_PORTFOLIO_FAILED"
    }
}