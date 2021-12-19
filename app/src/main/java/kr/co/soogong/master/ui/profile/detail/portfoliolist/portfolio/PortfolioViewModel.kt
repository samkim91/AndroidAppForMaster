package kr.co.soogong.master.ui.profile.detail.portfoliolist.portfolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.GetPortfolioUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePortfolioUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val savePortfolioUseCase: SavePortfolioUseCase,
    private val getPortfolioUseCase: GetPortfolioUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    val id = PortfolioFragment.getPortfolioId(savedStateHandle)     // 추가할 땐 null, 수정할 땐 포트폴리오 id

    val title = MutableLiveData<String>()
    val imageBeforeRepairing = ListLiveData<AttachmentDto>()
    val imageAfterRepairing = ListLiveData<AttachmentDto>()
    val description = MutableLiveData<String>()

    init {
        requestPortfolio()
    }

    private fun requestPortfolio() {
        Timber.tag(TAG).d("requestPortfolio: $id")
        id?.let {
            getPortfolioUseCase(id, CodeTable.PORTFOLIO.code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { portfolio ->
                        portfolio.title?.let { title.postValue(it) }
                        portfolio.beforeImage?.let { imageBeforeRepairing.add(it) }
                        portfolio.afterImage?.let { imageAfterRepairing.add(it) }
                        portfolio.description?.let { description.postValue(it) }
                    },
                    onError = { setAction(REQUEST_FAILED) }
                ).addToDisposable()
        }
    }

    fun savePortfolio() {
        Timber.tag(TAG).d("savePortfolio: $id")
        savePortfolioUseCase(
            portfolio = PortfolioDto(
                id = id,
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
            .doAfterTerminate { setAction(DISMISS_LOADING) }
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