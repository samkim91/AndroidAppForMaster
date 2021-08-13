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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditWarrantyInformationViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()

    val warrantyPeriod = MutableLiveData<Int>()
    val warrantyPeriodForLayout = MutableLiveData<String>()
    val warrantyDescription = MutableLiveData("")

    fun requestWarrantyInformation() {
        Timber.tag(TAG).d("requestWarrantyInformation: ")

        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    _profile.value = profile
                    profile.requiredInformation?.warrantyInformation?.warrantyPeriod?.let {
                        warrantyPeriod.postValue(it)
                        warrantyPeriodForLayout.postValue(
                            BottomDialogItem.getWarrantyPeriodList().find { item ->
                                item.value == it
                            }?.key)
                    }
                    profile.requiredInformation?.warrantyInformation?.warrantyDescription?.let {
                        warrantyDescription.postValue(it)
                    }
                },
                onError = { setAction(GET_WARRANTY_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    fun saveWarrantyInfo() {
        Timber.tag(TAG).d("saveWarrantyInfo: ")

        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                warrantyPeriod = warrantyPeriod.value,
                warrantyDescription = warrantyDescription.value,
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_WARRANTY_INFORMATION_SUCCESSFULLY) },
                onError = { setAction(SAVE_WARRANTY_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditWarrantyInformationViewModel"
        const val SAVE_WARRANTY_INFORMATION_SUCCESSFULLY = "SAVE_WARRANTY_INFORMATION_SUCCESSFULLY"
        const val SAVE_WARRANTY_INFORMATION_FAILED = "SAVE_WARRANTY_INFORMATION_FAILED"
        const val GET_WARRANTY_INFORMATION_FAILED = "GET_WARRANTY_INFORMATION_FAILED"
    }
}