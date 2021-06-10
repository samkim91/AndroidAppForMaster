package kr.co.soogong.master.ui.profile.edit.requiredinformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.profile.RequiredInformation
import kr.co.soogong.master.domain.usecase.auth.GetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.profile.GetRequiredInformationUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveCareerPeriodUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditRequiredInformationViewModel @Inject constructor(
    getMasterApprovalUseCase: GetMasterApprovalUseCase,
    private val getRequiredInformationUseCase: GetRequiredInformationUseCase,
    private val saveCareerPeriodUseCase: SaveCareerPeriodUseCase,
) : BaseViewModel() {
    private val _isApprovedMaster = MutableLiveData<Boolean>(getMasterApprovalUseCase())
    val isApprovedMaster: LiveData<Boolean>
        get() = _isApprovedMaster

    val requiredInformation = MutableLiveData<RequiredInformation>()

    fun requestRequiredInformation() {
        Timber.tag(TAG).d("requestRequiredInformation: ")
        getRequiredInformationUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { requiredInformation.value = it },
                onError = { }
            ).addToDisposable()
    }

    fun saveCareerPeriod(period: String, periodInt: Int) {
        Timber.tag(TAG).d("saveCareerPeriod: ")
        saveCareerPeriodUseCase(period)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_CAREER_PERIOD_SUCCESSFULLY) },
                onError = { setAction(SAVE_CAREER_PERIOD_FAILED) }
            ).addToDisposable()
    }

    fun saveServiceArea(radius: Int) {
        Timber.tag(TAG).d("saveServiceArea: ")
        // Todo.. serviceArea 저장하는 로직 추가 필요
    }

    companion object {
        private const val TAG = "EditRequiredInformationViewModel"
        const val SAVE_CAREER_PERIOD_SUCCESSFULLY = "SAVE_CAREER_PERIOD_SUCCESSFULLY"
        const val SAVE_CAREER_PERIOD_FAILED = "SAVE_CAREER_PERIOD_FAILED"
        const val GET_CAREER_PERIOD_FAILED = "GET_CAREER_PERIOD_FAILED"
    }
}