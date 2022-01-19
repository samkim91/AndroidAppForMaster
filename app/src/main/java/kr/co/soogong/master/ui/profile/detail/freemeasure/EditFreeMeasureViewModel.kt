package kr.co.soogong.master.ui.profile.detail.freemeasure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.global.CodeTable
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateFreeMeasureYnUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditFreeMeasureViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
    private val updateFreeMeasureYnUseCase: UpdateFreeMeasureYnUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    private val _freeMeasureOptions =
        MutableLiveData(listOf(CodeTable.POSSIBLE, CodeTable.IMPOSSIBLE))
    val freeMeasureOptions: LiveData<List<CodeTable>>
        get() = _freeMeasureOptions

    val freeMeasure = MutableLiveData<CodeTable>(_freeMeasureOptions.value?.get(0))

    fun requestFreeMeasure() {
        requestProfile {
            it.basicInformation?.freeMeasureYn?.let { boolean ->
                Timber.tag(TAG).d("requestFreeMeasure: $boolean")
                freeMeasure.postValue(_freeMeasureOptions.value?.find { options ->
                    options.asValue == boolean
                })
            }
        }
    }

    fun saveFreeMeasure() {
        saveMasterV2(
            updateFreeMeasureYnUseCase(
                MasterDto(
                    id = profile.value?.id,
                    uid = profile.value?.uid,
                    freeMeasureYn = freeMeasure.value?.asValue as Boolean
                )
            )
        )
    }

    companion object {
        private const val TAG = "EditIntroductionViewModel"
    }
}