package kr.co.soogong.master.ui.profile.edit.requiredinformation.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
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
    val mainAddress = MutableLiveData("")
    val subAddress = MutableLiveData("")
    var latitude = MutableLiveData(0.0)
    var longitude = MutableLiveData(0.0)


    fun getCompanyAddress() {
        Timber.tag(TAG).d("getBriefIntro: ")
        viewModelScope.launch {
            getCompanyAddressUseCase().let {
                mainAddress.postValue(it.mainAddress)
                subAddress.postValue(it.subAddress)
            }
        }
    }

    fun saveCompanyAddress() {
        Timber.tag(TAG).d("saveBriefIntro: ")
        mainAddress.value?.let {
            saveCompanyAddressUseCase(
                companyAddress = CompanyAddress(
                    mainAddress = mainAddress.value!!,
                    subAddress = subAddress.value ?: ""
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
    }
}