package kr.co.soogong.master.ui.profile.edit.portfolio

import android.net.Uri
import android.view.View
import android.widget.CompoundButton
import androidx.core.net.toUri
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.R
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.profile.Portfolio
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.domain.usecase.*
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.utils.ListLiveData
import kr.co.soogong.master.util.Event
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditPortfolioViewModel @Inject constructor(
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
    private val setPortfolioUseCase: SetPortfolioUseCase,
    private val getPortfolioUseCase: GetPortfolioUseCase
) : BaseViewModel() {

    val jobTitle = MutableLiveData("")
    val imageBeforeJob = MutableLiveData(Uri.EMPTY)
    val imageAfterJob = MutableLiveData(Uri.EMPTY)
    val jobDescription = MutableLiveData("")

    fun getPortfolio(portfolioId: Int) {
        // Todo.. 수정할 포트폴리오 가져오기
        val portfolio = getPortfolioUseCase(portfolioId)
        jobTitle.postValue(portfolio.jobTitle)
        imageBeforeJob.postValue(portfolio.imageBeforeJob.toUri())
        imageAfterJob.postValue(portfolio.imageAfterJob.toUri())
        jobDescription.postValue(portfolio.jobDescription)
    }

    fun savePortfolio(portfolioId: Int) {
        setPortfolioUseCase(
            portfolio = Portfolio(
                masterId = getMasterKeyCodeUseCase()!!,
                portfolioId = portfolioId,
                jobTitle = jobTitle.value!!,
                imageBeforeJob = imageBeforeJob.value.toString(),
                imageAfterJob = imageAfterJob.value.toString(),
                jobDescription = jobDescription.value!!
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