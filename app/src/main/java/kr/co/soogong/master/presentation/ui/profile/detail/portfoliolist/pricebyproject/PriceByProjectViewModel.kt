package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.pricebyproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.profile.portfolio.SavePriceByProjectDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.portfolio.SavePriceByProjectUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PriceByProjectViewModel @Inject constructor(
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val savePriceByProjectUseCase: SavePriceByProjectUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    val priceByProject = PriceByProjectFragment.getPriceByProject(savedStateHandle)

    val title = MutableLiveData<String>()
    val price = MutableLiveData(0L)
    val description = MutableLiveData<String>()

    init {
        setInitialPriceByProject()
    }

    private fun setInitialPriceByProject() {
        Timber.tag(TAG).d("setInitialPriceByProject: ")

        priceByProject.value?.let { priceByProjectDto ->
            priceByProjectDto.title?.let { title.postValue(it) }
            priceByProjectDto.price?.let { price.postValue(it.toLong()) }
            priceByProjectDto.description?.let { description.postValue(it) }
        }
    }

    fun savePriceByProject() {
        Timber.tag(TAG).d("savePriceByProject: $priceByProject")
        savePriceByProjectUseCase(
            SavePriceByProjectDto(
                id = priceByProject.value?.id,
                masterId = getMasterIdFromSharedUseCase(),
                title = title.value!!,
                description = description.value!!,
                price = price.value?.toInt()!!,
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("savePriceByProject successfully: $it")
                    setAction(SAVE_PRICE_BY_PROJECT_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("savePriceByProject failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditPortfolioViewModel"

        const val SAVE_PRICE_BY_PROJECT_SUCCESSFULLY = "SAVE_PRICE_BY_PROJECT_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}