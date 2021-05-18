package kr.co.soogong.master.ui.profile.edit.requiredinformation.warrantyinformation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.profile.WarrantyInformation
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
    val warrantyPeriodInt =  MutableLiveData(0)
    val warrantyDescription = MutableLiveData("")

    fun getWarrantyInfo() {
        Timber.tag(TAG).d("getWarrantyInfo: ")
        viewModelScope.launch {
            getWarrantyInformationUseCase().let { warrantyInformation ->
                warrantyPeriod.postValue(warrantyInformation.warrantyPeriod)
                warrantyDescription.postValue(warrantyInformation.warrantyDescription)
            }
        }
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
    }
}