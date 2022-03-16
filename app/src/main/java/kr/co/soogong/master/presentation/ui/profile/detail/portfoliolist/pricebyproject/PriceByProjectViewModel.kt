package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.pricebyproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
            title.postValue(priceByProjectDto.title)
            price.postValue(priceByProjectDto.price.toLong())
            description.postValue(priceByProjectDto.description)
        }
    }

    fun savePriceByProject() {
        Timber.tag(TAG).d("savePriceByProject: $priceByProject")
        viewModelScope.launch {
            try {
                savePriceByProjectUseCase(
                    SavePriceByProjectDto(
                        id = priceByProject.value?.id,
                        masterId = getMasterIdFromSharedUseCase(),
                        title = title.value!!,
                        description = description.value ?: "",
                        price = price.value?.toInt()!!,
                    )
                )

                Timber.tag(TAG).d("savePriceByProject successfully: ")
                setAction(SAVE_PRICE_BY_PROJECT_SUCCESSFULLY)
            } catch (e: Exception) {
                Timber.tag(TAG).d("savePriceByProject failed: $e")
                setAction(REQUEST_FAILED)
            }
        }
    }

    companion object {
        private val TAG = PriceByProjectViewModel::class.java.name
        const val SAVE_PRICE_BY_PROJECT_SUCCESSFULLY = "SAVE_PRICE_BY_PROJECT_SUCCESSFULLY"
    }
}