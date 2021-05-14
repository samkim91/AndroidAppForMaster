package kr.co.soogong.master.ui.profile.edit.withcard.pricebyproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.profile.PriceByProject
import kr.co.soogong.master.domain.usecase.*
import kr.co.soogong.master.domain.usecase.profile.GetPriceByProjectUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePriceByProjectUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
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
        viewModelScope.launch {
            getPriceByProjectUseCase(priceByProjectId).let { priceByProject ->
                title.postValue(priceByProject.title)
                price.postValue(priceByProject.projectPrice)
                description.postValue(priceByProject.description)
            }
        }
    }

    fun savePriceByProject(priceByProjectId: Int) {
        Timber.tag(TAG).d("getPriceByProject $priceByProjectId")
        savePriceByProjectUseCase(
            PriceByProject(
                itemId = priceByProjectId,
                title = title.value!!,
                projectPrice = price.value!!,
                description = description.value!!
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {},
                onError = {}
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditPortfolioViewModel"


    }
}