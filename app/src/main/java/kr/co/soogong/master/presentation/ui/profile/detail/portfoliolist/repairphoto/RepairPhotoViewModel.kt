package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.repairphoto

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.data.entity.profile.PortfolioDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.common.major.Project
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.SavePortfolioUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RepairPhotoViewModel @Inject constructor(
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val savePortfolioUseCase: SavePortfolioUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    val maxPhoto: Int = 5
    val maxProject: Int = 1

    val portfolio = RepairPhotoFragment.getPortfolio(savedStateHandle)

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val project = MutableLiveData<Project>()
    val repairPhotos = ListLiveData<AttachmentDto>()

    init {
        setInitialPortfolio()
    }

    private fun setInitialPortfolio() {
        Timber.tag(TAG).d("setInitialPortfolio: $portfolio")

        portfolio.value?.let { portfolioDto ->
            portfolioDto.title?.let { title.postValue(it) }
            portfolioDto.afterImage?.let { repairPhotos.add(it) }
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
            beforeImageUri = null,
            afterImageUri = repairPhotos.value?.first()?.uri,
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

    fun startRepairPhotosPicker() {
        Timber.tag(TAG).d("startImagePickerForBefore: ")
        setAction(START_REPAIR_PHOTOS_PICKER)
    }

    companion object {
        private val TAG = RepairPhotoViewModel::class.java.name
        const val SAVE_PORTFOLIO_SUCCESSFULLY = "SAVE_PORTFOLIO_SUCCESSFULLY"
        const val START_REPAIR_PHOTOS_PICKER = "START_REPAIR_PHOTOS_PICKER"
    }
}