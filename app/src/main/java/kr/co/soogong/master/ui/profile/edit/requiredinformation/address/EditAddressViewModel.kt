package kr.co.soogong.master.ui.profile.edit.requiredinformation.address

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.profile.CompanyAddress
import kr.co.soogong.master.domain.usecase.profile.*
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditAddressViewModel @Inject constructor(
    private val getCompanyAddressUseCase: GetCompanyAddressUseCase,
    private val saveCompanyAddressUseCase: SaveCompanyAddressUseCase,
) : BaseViewModel() {
    val roadAddress = MutableLiveData("")
    val detailAddress = MutableLiveData("")
    var latitude = MutableLiveData(0.0)
    var longitude = MutableLiveData(0.0)


    fun requestCompanyAddress() {
        Timber.tag(TAG).d("requestCompanyAddress: ")

        getCompanyAddressUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    roadAddress.postValue(it.roadAddress)
                    detailAddress.postValue(it.detailAddress)
                },
                onError = { setAction(GET_COMPANY_ADDRESS_FAILED) }
            ).addToDisposable()
    }

    fun saveCompanyAddress() {
        Timber.tag(TAG).d("saveCompanyAddress: ")

        roadAddress.value?.let {
            saveCompanyAddressUseCase(
                companyAddress = CompanyAddress(
                    roadAddress = roadAddress.value!!,
                    detailAddress = detailAddress.value ?: ""
                ),
                latitude = latitude.value!!,
                longitude = longitude.value!!
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_COMPANY_ADDRESS_SUCCESSFULLY) },
                    onError = { setAction(SAVE_COMPANY_ADDRESS_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditAddressViewModel"
        const val SAVE_COMPANY_ADDRESS_SUCCESSFULLY = "SAVE_COMPANY_ADDRESS_SUCCESSFULLY"
        const val SAVE_COMPANY_ADDRESS_FAILED = "SAVE_COMPANY_ADDRESS_FAILED"
        const val GET_COMPANY_ADDRESS_FAILED = "GET_COMPANY_ADDRESS_FAILED"
    }
}