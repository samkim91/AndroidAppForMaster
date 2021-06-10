package kr.co.soogong.master.ui.profile.edit.requiredinformation.warrantyinformation

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.profile.WarrantyInformation
import kr.co.soogong.master.domain.usecase.profile.GetWarrantyInformationUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveWarrantyInformationUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditWarrantyInformationViewModel @Inject constructor(
    private val getWarrantyInformationUseCase: GetWarrantyInformationUseCase,
    private val saveWarrantyInformationUseCase: SaveWarrantyInformationUseCase,
) : BaseViewModel() {
    val warrantyPeriod = MutableLiveData("")
    val warrantyPeriodInt = MutableLiveData(0)
    val warrantyDescription = MutableLiveData("")

    fun requestWarrantyInformation() {
        Timber.tag(TAG).d("requestWarrantyInformation: ")
        getWarrantyInformationUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { warrantyInformation ->
                    warrantyPeriod.postValue(warrantyInformation.warrantyPeriod)
                    warrantyDescription.postValue(warrantyInformation.warrantyDescription)
                },
                onError = { setAction(GET_WARRANTY_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    fun saveWarrantyInfo() {
        Timber.tag(TAG).d("saveWarrantyInfo: ")
        (!warrantyPeriod.value.isNullOrEmpty() && !warrantyDescription.value.isNullOrEmpty()).let {
            saveWarrantyInformationUseCase(
                WarrantyInformation(
                    warrantyPeriod = warrantyPeriod.value!!,
                    warrantyDescription = warrantyDescription.value!!
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_WARRANTY_INFORMATION_SUCCESSFULLY) },
                    onError = { setAction(SAVE_WARRANTY_INFORMATION_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditWarrantyInformationViewModel"
        const val SAVE_WARRANTY_INFORMATION_SUCCESSFULLY = "SAVE_WARRANTY_INFORMATION_SUCCESSFULLY"
        const val SAVE_WARRANTY_INFORMATION_FAILED = "SAVE_WARRANTY_INFORMATION_FAILED"
        const val GET_WARRANTY_INFORMATION_FAILED = "GET_WARRANTY_INFORMATION_FAILED"
    }
}