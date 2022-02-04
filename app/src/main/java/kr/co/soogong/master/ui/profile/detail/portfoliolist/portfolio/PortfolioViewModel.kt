package kr.co.soogong.master.ui.profile.detail.portfoliolist.portfolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.data.global.CodeTable
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePortfolioUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
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
            portfolioDto.title?.let { title.postValue(it) }
            portfolioDto.beforeImage?.let { imageBeforeRepairing.add(it) }
            portfolioDto.afterImage?.let { imageAfterRepairing.add(it) }
            portfolioDto.description?.let { description.postValue(it) }
        }
    }

    fun savePortfolio() {
        Timber.tag(TAG).d("savePortfolio: $portfolio")
        savePortfolioUseCase(
            portfolio = PortfolioDto(
                id = portfolio.value?.id,
                masterId = getMasterIdFromSharedUseCase(),
                title = title.value,
                description = description.value,
                type = CodeTable.PORTFOLIO.code,
            ),
            beforeImageUri = imageBeforeRepairing.value?.first()?.uri,
            afterImageUri = imageAfterRepairing.value?.first()?.uri,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setAction(SHOW_LOADING) }
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("savePortfolio successfully: $it")
                    setAction(SAVE_PORTFOLIO_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("savePortfolio failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
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
        private const val TAG = "EditPortfolioViewModel"
        const val SAVE_PORTFOLIO_SUCCESSFULLY = "SAVE_PORTFOLIO_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val START_IMAGE_PICKER_FOR_BEFORE = "START_IMAGE_PICKER_FOR_BEFORE"
        const val START_IMAGE_PICKER_FOR_AFTER = "START_IMAGE_PICKER_FOR_AFTER"
    }
}