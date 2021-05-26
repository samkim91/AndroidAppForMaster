package kr.co.soogong.master.ui.profile.edit.requiredinformation.businessunitinformation

import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.estimation.ImagePath
import kr.co.soogong.master.data.profile.BusinessUnitInformation
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
    val businessUnitType = MutableLiveData<String>()
    val businessName = MutableLiveData("")
    val companyName = MutableLiveData("")
    val identicalNumber = MutableLiveData("")
    val identicalImage = MutableLiveData(Uri.EMPTY)
    val birthday = MutableLiveData("")

    fun getBusinessUnitInfo() {
        Timber.tag(TAG).d("getBusinessUnitInfo: ")
        viewModelScope.launch {
            getBusinessUnitInformationUseCase().let {
                businessUnitType.postValue(it.businessUnitType)
                if (it.businessUnitType == "프리랜서") {
                    birthday.postValue(it.identicalNumber.toString())
                } else {
                    businessName.postValue(it.businessName)
                    companyName.postValue(it.companyName)
                    identicalImage.postValue(Uri.parse(it.identicalImage?.path))
                    identicalNumber.postValue(it.identicalNumber.toString())
                }
            }
        }
    }

    fun saveBusinessUnitInfo() {
        Timber.tag(TAG).d("saveBriefIntro: ")
        saveBusinessUnitInformationUseCase(
            BusinessUnitInformation(
                businessUnitType = businessUnitType.value!!,
                businessName = businessName.value!!,
                companyName = companyName.value!!,
                identicalNumber = if (businessUnitType.value == "프리랜서") birthday.value!!.toInt() else identicalNumber.value!!.toInt(),
                identicalImage = ImagePath(identicalImage.value.toString())
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_BUSINESS_UNIT_INFORMATION_SUCCESSFULLY) },
                onError = { setAction(SAVE_BUSINESS_UNIT_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    fun clearImage(v: View) = identicalImage.postValue(Uri.EMPTY)


    companion object {
        private const val TAG = "EditBusinessUnitInformationViewModel"
        const val SAVE_BUSINESS_UNIT_INFORMATION_SUCCESSFULLY =
            "SAVE_BUSINESS_UNIT_INFORMATION_SUCCESSFULLY"
        const val SAVE_BUSINESS_UNIT_INFORMATION_FAILED = "SAVE_BUSINESS_UNIT_INFORMATION_FAILED"
    }
}