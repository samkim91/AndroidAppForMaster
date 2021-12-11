package kr.co.soogong.master.ui.profile.detail.portfoliolist.pricebyproject

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.GetPortfolioUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePortfolioUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PriceByProjectViewModel @Inject constructor(
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val savePortfolioUseCase: SavePortfolioUseCase,
    private val getPortfolioUseCase: GetPortfolioUseCase,
) : BaseViewModel() {
    val id = MutableLiveData<Int>()
    val title = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    fun requestPriceByProject(priceByProjectId: Int) {
        Timber.tag(TAG).d("requestPriceByProject: $priceByProjectId")
        getPortfolioUseCase(priceByProjectId, "price")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { priceByProject ->
                    priceByProject.id?.let { id.postValue(it) }
                    priceByProject.title?.let { title.postValue(it) }
                    priceByProject.price?.let { price.postValue(it.toString()) }
                    priceByProject.description?.let { description.postValue(it) }
                },
                onError = { setAction(GET_PRICE_BY_PROJECT_FAILED) }
            ).addToDisposable()
    }

    fun savePriceByProject() {
        Timber.tag(TAG).d("savePriceByProject: ${id.value}")
        savePortfolioUseCase(
            PortfolioDto(
                id = if (id.value == -1) null else id.value,
                masterId = getMasterIdFromSharedUseCase(),
                title = title.value!!,
                description = description.value,
                type = CodeTable.PRICE_BY_PROJECT.code,
                price = price.value?.replace(",", "")?.toInt(),
            ),
            beforeImageUri = null,
            afterImageUri = null,
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("savePriceByProject successfully: $it")
                    setAction(SAVE_PRICE_BY_PROJECT_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("savePriceByProject failed: $it")
                    setAction(SAVE_PRICE_BY_PROJECT_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditPortfolioViewModel"

        const val SAVE_PRICE_BY_PROJECT_SUCCESSFULLY = "SAVE_PRICE_BY_PROJECT_SUCCESSFULLY"
        const val SAVE_PRICE_BY_PROJECT_FAILED = "SAVE_PRICE_BY_PROJECT_FAILED"
        const val GET_PRICE_BY_PROJECT_FAILED = "GET_PRICE_BY_PROJECT_FAILED"
    }
}