package kr.co.soogong.master.ui.profile.edit.requiredinformation.businessunitinformation

import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.ImagePath
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
    val businessType = MutableLiveData<String>()
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
                onSuccess = {
                    businessType.postValue(it.businessType)
                    if (it.businessType == "프리랜서") {
                        birthday.postValue(it.businessNumber.toString())
                    } else {
                        businessName.postValue(it.businessName)
                        shopName.postValue(it.shopName)
                        businessRegistImage.postValue(Uri.parse(it.businessRegistImage?.path))
                        businessNumber.postValue(it.businessNumber.toString())
                    }
                },
                onError = { setAction(GET_BUSINESS_UNIT_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    fun saveBusinessUnitInfo() {
        Timber.tag(TAG).d("saveBriefIntro: ")
        saveBusinessUnitInformationUseCase(
            BusinessUnitInformation(
                businessType = businessType.value!!,
                businessName = businessName.value!!,
                shopName = shopName.value!!,
                businessNumber = if (businessType.value == "프리랜서") birthday.value!! else businessNumber.value!!,
                businessRegistImage = ImagePath(businessRegistImage.value.toString())
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