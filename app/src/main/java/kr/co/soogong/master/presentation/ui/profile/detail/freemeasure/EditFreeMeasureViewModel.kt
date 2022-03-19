package kr.co.soogong.master.presentation.ui.profile.detail.freemeasure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateFreeMeasureYnUseCase
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditFreeMeasureViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
    private val updateFreeMeasureYnUseCase: UpdateFreeMeasureYnUseCase,
) : EditProfileViewModel(getProfileUseCase, saveMasterUseCase) {

    private val _freeMeasureOptions =
        MutableLiveData(listOf(CodeTable.POSSIBLE, CodeTable.IMPOSSIBLE))

    val freeMeasureOptions: LiveData<List<CodeTable>>
        get() = _freeMeasureOptions

    val freeMeasure = MutableLiveData<CodeTable>(_freeMeasureOptions.value?.get(0))

    init {
        requestFreeMeasure()
    }

    private fun requestFreeMeasure() {
        requestProfile {
            it.basicInformation.freeMeasureYn.let { boolean ->
                Timber.tag(TAG).d("requestFreeMeasure: $boolean")
                freeMeasure.postValue(_freeMeasureOptions.value?.find { options ->
                    options.additional == boolean
                })
            }
        }
    }

    fun saveFreeMeasure() {
        viewModelScope.launch {
            try {
                updateFreeMeasureYnUseCase(
                    MasterDto(
                        id = profile.value?.id,
                        uid = profile.value?.uid,
                        freeMeasureYn = true // freeMeasure.value?.asValue as Boolean
                    )
                )

                setAction(REQUEST_SUCCESS)
            } catch (e: Exception) {
                Timber.tag(TAG).e("saveFreeMeasure failed: $e")
                setAction(REQUEST_FAILED)
            }
        }
    }

    companion object {
        private const val TAG = "EditIntroductionViewModel"
    }
}