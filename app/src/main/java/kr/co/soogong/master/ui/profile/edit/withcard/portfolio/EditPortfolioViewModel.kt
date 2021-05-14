package kr.co.soogong.master.ui.profile.edit.withcard.portfolio

import android.net.Uri
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.profile.Portfolio
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.domain.usecase.profile.GetPortfolioUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePortfolioUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditPortfolioViewModel @Inject constructor(
    private val savePortfolioUseCase: SavePortfolioUseCase,
    private val getPortfolioUseCase: GetPortfolioUseCase
) : BaseViewModel() {
    val title = MutableLiveData("")
    val imageBeforeJob = MutableLiveData(Uri.EMPTY)
    val imageAfterJob = MutableLiveData(Uri.EMPTY)
    val description = MutableLiveData("")

    fun getPortfolio(portfolioId: Int) {
        Timber.tag(TAG).d("getPortfolio: $portfolioId")

        viewModelScope.launch {
            getPortfolioUseCase(portfolioId).let { portfolio ->
                title.postValue(portfolio.title)
                imageBeforeJob.postValue(portfolio.imageBeforeJob.toUri())
                imageAfterJob.postValue(portfolio.imageAfterJob.toUri())
                description.postValue(portfolio.description)
            }
        }
    }

    fun savePortfolio(portfolioId: Int) {
        Timber.tag(TAG).d("getPortfolio: $portfolioId")
        savePortfolioUseCase(
            portfolio = Portfolio(
                itemId = portfolioId,
                title = title.value!!,
                imageBeforeJob = imageBeforeJob.value.toString(),
                imageAfterJob = imageAfterJob.value.toString(),
                description = description.value!!
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {},
                onError = {}
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


    }
}