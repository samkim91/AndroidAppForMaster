package kr.co.soogong.master.ui.profile.detail.freeMeasure

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import javax.inject.Inject

@HiltViewModel
class EditFreeMeasureViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val agree = MutableLiveData<Boolean>()
    val disagree = MutableLiveData<Boolean>()

    fun requestFreeMeasure() {
        requestProfile {
            it.basicInformation?.freeMeasureYn?.let { boolean ->
                if (boolean) agree.postValue(true) else disagree.postValue(true)
            }
        }
    }

    fun saveFreeMeasure() {
        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                freeMeasureYn = agree.value == true
            )
        )
    }

    companion object {
        private const val TAG = "EditIntroductionViewModel"
    }
}