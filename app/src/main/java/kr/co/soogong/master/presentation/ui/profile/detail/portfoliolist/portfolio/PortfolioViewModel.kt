package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.portfolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.data.entity.profile.portfolio.SavePortfolioDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.portfolio.SavePortfolioUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val savePortfolioUseCase: SavePortfolioUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    val portfolio = PortfolioFragment.getPortfolio(savedStateHandle)

    val title = MutableLiveData<String>()
    val imageBeforeRepairing = ListLiveData<AttachmentDto>()
    val imageAfterRepairing = ListLiveData<AttachmentDto>()
    val description = MutableLiveData<String>()

    init {
        setInitialPortfolio()
    }

    private fun setInitialPortfolio() {
        Timber.tag(TAG).d("setInitialPortfolio: $portfolio")

        portfolio.value?.let { portfolioDto ->
            portfolioDto.title.let { title.postValue(it) }
            portfolioDto.beforeImage?.let { imageBeforeRepairing.add(it) }
            portfolioDto.afterImage?.let { imageAfterRepairing.add(it) }
            portfolioDto.description.let { description.postValue(it) }
        }
    }

    fun savePortfolio() {
        Timber.tag(TAG).d("savePortfolio: $portfolio")
        viewModelScope.launch {
            try {
                setAction(SHOW_LOADING)

                savePortfolioUseCase(
                    SavePortfolioDto(
                        id = portfolio.value?.id,
                        masterId = getMasterIdFromSharedUseCase(),
                        title = title.value!!,
                        description = description.value ?: "",
                    ),
                    imageBeforeRepairing.value?.first()?.uri,
                    imageAfterRepairing.value?.first()?.uri
                )

                Timber.tag(TAG).d("savePortfolio successfully: ")
                setAction(DISMISS_LOADING)
                setAction(SAVE_PORTFOLIO_SUCCESSFULLY)
            } catch (e: Exception) {
                Timber.tag(TAG).d("savePortfolio failed: $e")
                setAction(REQUEST_FAILED)
            }
        }
    }

    fun startImagePickerForBefore() {
        Timber.tag(TAG).d("startImagePickerForBefore: ")
        setAction(START_IMAGE_PICKER_FOR_BEFORE)
    }

    fun startImagePickerForAfter() {
        Timber.tag(TAG).d("startImagePickerForAfter: ")
        setAction(START_IMAGE_PICKER_FOR_AFTER)
    }

    companion object {
        private val TAG = PortfolioViewModel::class.java.name
        const val SAVE_PORTFOLIO_SUCCESSFULLY = "SAVE_PORTFOLIO_SUCCESSFULLY"
        const val START_IMAGE_PICKER_FOR_BEFORE = "START_IMAGE_PICKER_FOR_BEFORE"
        const val START_IMAGE_PICKER_FOR_AFTER = "START_IMAGE_PICKER_FOR_AFTER"
    }
}