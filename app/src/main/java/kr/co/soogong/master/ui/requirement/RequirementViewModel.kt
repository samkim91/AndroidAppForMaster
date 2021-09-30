package kr.co.soogong.master.ui.requirement

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RequirementViewModel @Inject constructor(
    private val getMasterSimpleInfoUseCase: GetMasterSimpleInfoUseCase,
) : BaseViewModel() {

    private val _masterSimpleInfo = MutableLiveData<MasterDto>()
    val masterSimpleInfo: LiveData<MasterDto>
        get() = _masterSimpleInfo

    fun requestMasterSimpleInfo() {
        Timber.tag(TAG).d("requestMasterSimpleInfo: ")
        getMasterSimpleInfoUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo successful: $it")
                    _masterSimpleInfo.value = it
                },
                onError = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun activateRequestingMeasurement(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("activateRequestingMeasurement: $isChecked")
        // TODO: 2021/09/30 Re-4차 개발 예정
    }

    companion object {
        private const val TAG = "RequirementViewModel"

        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}