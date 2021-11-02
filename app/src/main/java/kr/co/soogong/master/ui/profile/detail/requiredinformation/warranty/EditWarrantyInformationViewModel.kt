package kr.co.soogong.master.ui.profile.detail.requiredinformation.warranty

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
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogItem
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditWarrantyInformationViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val warrantyPeriod = MutableLiveData<Int>()
    val warrantyPeriodForLayout = MutableLiveData<String>()
    val warrantyDescription = MutableLiveData("")

    fun requestWarrantyInformation() {
        Timber.tag(TAG).d("requestWarrantyInformation: ")

        requestProfile {
            profile.value = it
            it.requiredInformation?.warrantyInformation?.warrantyPeriod?.let { period ->
                warrantyPeriod.postValue(period)
                warrantyPeriodForLayout.postValue(
                    BottomDialogItem.getWarrantyPeriodList().find { item ->
                        item.value == period
                    }?.key)
            }
            it.requiredInformation?.warrantyInformation?.warrantyDescription?.let { description ->
                warrantyDescription.postValue(description)
            }
        }
    }

    fun saveWarrantyInfo() {
        Timber.tag(TAG).d("saveWarrantyInfo: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                warrantyPeriod = warrantyPeriod.value,
                warrantyDescription = warrantyDescription.value,
            )
        )
    }

    companion object {
        private const val TAG = "EditWarrantyInformationViewModel"
    }
}