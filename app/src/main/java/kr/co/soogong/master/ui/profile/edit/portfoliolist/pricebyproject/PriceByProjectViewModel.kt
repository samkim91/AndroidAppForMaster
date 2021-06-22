package kr.co.soogong.master.ui.profile.edit.portfoliolist.pricebyproject

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
class PriceByProjectViewModel @Inject constructor(
    private val savePortfolioUseCase: SavePortfolioUseCase,
    private val getPortfolioUseCase: GetPortfolioUseCase,
) : BaseViewModel() {
    val title = MutableLiveData("")
    val price = MutableLiveData("")
    val description = MutableLiveData("")

    fun getPriceByProject(priceByProjectId: Int) {
        Timber.tag(TAG).d("getPriceByProject $priceByProjectId")
        getPortfolioUseCase(priceByProjectId, "price")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { priceByProject ->
                    title.postValue(priceByProject.title)
                    price.postValue(priceByProject.price?.toString())
                    description.postValue(priceByProject.description)
                },
                onError = { setAction(GET_PRICE_BY_PROJECT_FAILED) }
            ).addToDisposable()
    }

    fun savePriceByProject(priceByProjectId: Int) {
        Timber.tag(TAG).d("getPriceByProject $priceByProjectId")
        savePortfolioUseCase(
            PortfolioDto(
                id = priceByProjectId,
                title = title.value!!,
                description = description.value,
                type = "price",
                price = price.value?.replace(",", "")?.toInt(),
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_PRICE_BY_PROJECT_SUCCESSFULLY) },
                onError = { setAction(SAVE_PRICE_BY_PROJECT_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditPortfolioViewModel"

        const val SAVE_PRICE_BY_PROJECT_SUCCESSFULLY = "SAVE_PRICE_BY_PROJECT_SUCCESSFULLY"
        const val SAVE_PRICE_BY_PROJECT_FAILED = "SAVE_PRICE_BY_PROJECT_FAILED"
        const val GET_PRICE_BY_PROJECT_FAILED = "GET_PRICE_BY_PROJECT_FAILED"

    }
}