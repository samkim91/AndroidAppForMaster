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
import kr.co.soogong.master.domain.usecase.profile.GetPortfolioUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePortfolioUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val savePortfolioUseCase: SavePortfolioUseCase,
    private val getPortfolioUseCase: GetPortfolioUseCase
) : BaseViewModel() {
    val title = MutableLiveData("")
    val imageBeforeJob = MutableLiveData(Uri.EMPTY)
    val imageAfterJob = MutableLiveData(Uri.EMPTY)
    val description = MutableLiveData("")

    fun requestPortfolio(portfolioId: Int) {
        Timber.tag(TAG).d("requestPortfolio: $portfolioId")
        getPortfolioUseCase(portfolioId, "portfolio")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { portfolio ->
                    title.postValue(portfolio.title)
                    imageBeforeJob.postValue(portfolio.beforeRepairImageId?.toUri())
                    imageAfterJob.postValue(portfolio.afterRepairImageId?.toUri())
                    description.postValue(portfolio.description)
                },
                onError = { setAction(GET_PORTFOLIO_FAILED) }
            ).addToDisposable()
    }

    fun savePortfolio(portfolioId: Int) {
        Timber.tag(TAG).d("savePortfolio: $portfolioId")
        savePortfolioUseCase(
            portfolio = PortfolioDto(
                id = portfolioId,
                title = title.value!!,
                description = description.value,
                type = "portfolio",
                beforeRepairImageId = imageBeforeJob.value.toString(),  // TODO: 2021/06/22 이미지 업로드로 변경해야함 ...
                afterRepairImageId = imageAfterJob.value.toString(),
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_PORTFOLIO_SUCCESSFULLY) },
                onError = { setAction(SAVE_PORTFOLIO_FAILED) }
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