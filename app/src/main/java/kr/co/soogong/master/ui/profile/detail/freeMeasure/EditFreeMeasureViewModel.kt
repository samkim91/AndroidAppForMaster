package kr.co.soogong.master.ui.profile.detail.freeMeasure

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateFreeMeasureYnUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import javax.inject.Inject

@HiltViewModel
class EditFreeMeasureViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
    private val updateFreeMeasureYnUseCase: UpdateFreeMeasureYnUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val agreement = MutableLiveData<Boolean>(false)

    fun requestFreeMeasure() {
        requestProfile {
            it.basicInformation?.freeMeasureYn?.let { boolean ->
                if (boolean) agreement.postValue(boolean)
            }
        }
    }

    fun saveFreeMeasure() {
        saveMasterV2(
            updateFreeMeasureYnUseCase(
                MasterDto(
                    id = profile.value?.id,
                    uid = profile.value?.uid,
                    freeMeasureYn = agreement.value == true
                )
            )
        )
    }

    companion object {
        private const val TAG = "EditIntroductionViewModel"
    }
}