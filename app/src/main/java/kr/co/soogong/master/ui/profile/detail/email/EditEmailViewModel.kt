package kr.co.soogong.master.ui.profile.detail.email

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditEmailViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val localPart = MutableLiveData("")
    val domain = MutableLiveData("")

    fun requestEmail() {
        Timber.tag(TAG).d("requestEmail: ")

        requestProfile {
            profile.postValue(it)
            it.basicInformation?.email?.let { email ->
                if (email.contains("@")) {
                    email.split("@").let { split ->
                        localPart.postValue(split[0])
                        domain.postValue(split[1])
                    }
                } else {
                    localPart.postValue(email)
                }
            }
        }
    }

    fun saveEmailAddress() {
        Timber.tag(TAG).d("saveEmailAddress: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                email = "${localPart.value}@${domain.value}",
            )
        )
    }

    companion object {
        private const val TAG = "EditEmailViewModel"
    }
}