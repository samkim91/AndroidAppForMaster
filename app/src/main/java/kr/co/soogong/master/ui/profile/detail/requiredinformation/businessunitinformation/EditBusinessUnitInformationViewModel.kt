package kr.co.soogong.master.ui.profile.detail.requiredinformation.businessunitinformation

import android.net.Uri
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.CodeTable
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditBusinessUnitInformationViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile>
        get() = _profile

    val businessType = MutableLiveData("")
    val businessName = MutableLiveData("")
    val shopName = MutableLiveData("")
    val businessNumber = MutableLiveData("")
    val businessRegistImage = MutableLiveData(Uri.EMPTY)
    val birthday = MutableLiveData("")

    fun requestBusinessUnitInformation() {
        Timber.tag(TAG).d("requestBusinessUnitInformation: ")
        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { masterProfile ->
                    Timber.tag(TAG).d("requestBusinessUnitInformation successfully: $masterProfile")

                    this._profile.value = masterProfile
                    masterProfile.requiredInformation?.businessUnitInformation?.businessType?.let { businessType ->
                        this.businessType.postValue(businessType)
                        if (businessType == "프리랜서") {
                            masterProfile.requiredInformation.businessUnitInformation.businessNumber?.let {
                                birthday.postValue(it)
                            }
                        } else {
                            masterProfile.requiredInformation.businessUnitInformation.businessName?.let {
                                businessName.postValue(it)
                            }
                            masterProfile.requiredInformation.businessUnitInformation.shopName?.let {
                                shopName.postValue(it)
                            }
                            masterProfile.requiredInformation.businessUnitInformation.businessRegistImage?.url?.let {
                                businessRegistImage.postValue(it.toUri())
                            }
                            masterProfile.requiredInformation.businessUnitInformation.businessNumber?.let {
                                businessNumber.postValue(it)
                            }
                        }
                    }
                },
                onError = {
                    Timber.tag(TAG).d("requestBusinessUnitInformation failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveBusinessUnitInformation() {
        Timber.tag(TAG).d("saveBusinessUnitInformation: ")
        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                businessType = businessType.value?.let { CodeTable.findBusinessTypeByKorean(it).code },
                businessName = businessName.value,
                shopName = shopName.value,
                businessNumber = if (businessType.value == "프리랜서") birthday.value else businessNumber.value,
                approvedStatus = RequestApproveCodeTable.code,
            ),
            businessRegistImageUri = businessRegistImage.value,
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("saveBusinessUnitInformation successfully: $it")
                    setAction(SAVE_BUSINESS_INFORMATION_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("saveBusinessUnitInformation failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun clearImage(v: View) = businessRegistImage.postValue(Uri.EMPTY)

    companion object {
        private const val TAG = "EditBusinessUnitInformationViewModel"
        const val SAVE_BUSINESS_INFORMATION_SUCCESSFULLY = "SAVE_BUSINESS_INFORMATION_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}