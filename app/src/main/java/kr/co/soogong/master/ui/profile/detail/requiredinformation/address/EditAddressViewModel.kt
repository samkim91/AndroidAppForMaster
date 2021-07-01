package kr.co.soogong.master.ui.profile.detail.requiredinformation.address

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditAddressViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()

    val roadAddress = MutableLiveData("")
    val detailAddress = MutableLiveData("")
    val latitude = MutableLiveData(0.0)
    val longitude = MutableLiveData(0.0)

    fun requestCompanyAddress() {
        Timber.tag(TAG).d("requestCompanyAddress: ")

        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    _profile.value = profile
                    roadAddress.postValue(profile.requiredInformation?.companyAddress?.roadAddress)
                    detailAddress.postValue(profile.requiredInformation?.companyAddress?.detailAddress)
                },
                onError = { setAction(GET_COMPANY_ADDRESS_FAILED) }
            ).addToDisposable()
    }

    fun saveCompanyAddress() {
        Timber.tag(TAG).d("saveCompanyAddress: ")

        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                roadAddress = roadAddress.value,
                detailAddress = detailAddress.value,
                latitude = latitude.value?.toFloat(),
                longitude = longitude.value?.toFloat(),
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_COMPANY_ADDRESS_SUCCESSFULLY) },
                onError = { setAction(SAVE_COMPANY_ADDRESS_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditAddressViewModel"
        const val SAVE_COMPANY_ADDRESS_SUCCESSFULLY = "SAVE_COMPANY_ADDRESS_SUCCESSFULLY"
        const val SAVE_COMPANY_ADDRESS_FAILED = "SAVE_COMPANY_ADDRESS_FAILED"
        const val GET_COMPANY_ADDRESS_FAILED = "GET_COMPANY_ADDRESS_FAILED"
    }
}