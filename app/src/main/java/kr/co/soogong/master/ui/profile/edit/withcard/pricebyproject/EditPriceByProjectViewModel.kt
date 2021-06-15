package kr.co.soogong.master.ui.profile.edit.withcard.pricebyproject

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.profile.PriceByProject
import kr.co.soogong.master.domain.usecase.profile.GetPriceByProjectUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePriceByProjectUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditPriceByProjectViewModel @Inject constructor(
    private val savePriceByProjectUseCase: SavePriceByProjectUseCase,
    private val getPriceByProjectUseCase: GetPriceByProjectUseCase
) : BaseViewModel() {
    val title = MutableLiveData("")
    val price = MutableLiveData("")
    val description = MutableLiveData("")

    fun getPriceByProject(priceByProjectId: Int) {
        Timber.tag(TAG).d("getPriceByProject $priceByProjectId")
        getPriceByProjectUseCase(priceByProjectId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { priceByProject ->
                    title.postValue(priceByProject.title)
                    price.postValue(priceByProject.price)
                    description.postValue(priceByProject.description)
                },
                onError = { setAction(GET_PRICE_BY_PROJECT_FAILED) }
            ).addToDisposable()
    }

    fun savePriceByProject(priceByProjectId: Int) {
        Timber.tag(TAG).d("getPriceByProject $priceByProjectId")
        savePriceByProjectUseCase(
            PriceByProject(
                id = priceByProjectId,
                title = title.value!!,
                description = description.value!!,
                project = "",
                type = "price",
                price = price.value!!,
                createdAt = Date(),
                updatedAt = Date(),
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