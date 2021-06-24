package kr.co.soogong.master.ui.profile.detail.requiredinformation.businessunitinformation

import android.net.Uri
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.profile.BusinessUnitInformation
import kr.co.soogong.master.domain.usecase.profile.GetBusinessUnitInformationUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveBusinessUnitInformationUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditBusinessUnitInformationViewModel @Inject constructor(
    private val getBusinessUnitInformationUseCase: GetBusinessUnitInformationUseCase,
    private val saveBusinessUnitInformationUseCase: SaveBusinessUnitInformationUseCase,
) : BaseViewModel() {
    val businessType = MutableLiveData("")
    val businessName = MutableLiveData("")
    val shopName = MutableLiveData("")
    val businessNumber = MutableLiveData("")
    val businessRegistImage = MutableLiveData(Uri.EMPTY)
    val birthday = MutableLiveData("")

    fun requestBusinessUnitInformation() {
        Timber.tag(TAG).d("requestBusinessUnitInformation: ")

        getBusinessUnitInformationUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { businessInformation ->
                    businessType.postValue(businessInformation.businessType ?: "")
                    if (businessInformation.businessType == "프리랜서") {
                        birthday.postValue(businessInformation.businessNumber ?: "")
                    } else {
                        businessName.postValue(businessInformation.businessName ?: "")
                        shopName.postValue(businessInformation.shopName ?: "")
                        businessRegistImage.postValue(businessInformation.businessRegistImage?.url?.toUri() ?: Uri.EMPTY)
                        businessNumber.postValue(businessInformation.businessNumber ?: "")
                    }
                },
                onError = { setAction(GET_BUSINESS_UNIT_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    fun saveBusinessUnitInformation() {
        Timber.tag(TAG).d("saveBusinessUnitInformation: ")
        saveBusinessUnitInformationUseCase(
            BusinessUnitInformation(
                businessType = businessType.value!!,
                businessName = businessName.value!!,
                shopName = shopName.value!!,
                businessNumber = if (businessType.value == "프리랜서") birthday.value!! else businessNumber.value!!,
                businessRegistImage = null // TODO: 2021/06/23 이미지 업로드로 수정해야함 
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_BUSINESS_UNIT_INFORMATION_SUCCESSFULLY) },
                onError = { setAction(SAVE_BUSINESS_UNIT_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    fun clearImage(v: View) = businessRegistImage.postValue(Uri.EMPTY)

    companion object {
        private const val TAG = "EditBusinessUnitInformationViewModel"
        const val SAVE_BUSINESS_UNIT_INFORMATION_SUCCESSFULLY =
            "SAVE_BUSINESS_UNIT_INFORMATION_SUCCESSFULLY"
        const val SAVE_BUSINESS_UNIT_INFORMATION_FAILED = "SAVE_BUSINESS_UNIT_INFORMATION_FAILED"
        const val GET_BUSINESS_UNIT_INFORMATION_FAILED = "GET_BUSINESS_UNIT_INFORMATION_FAILED"
    }
}