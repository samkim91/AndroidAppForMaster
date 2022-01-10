package kr.co.soogong.master.ui.profile.detail.email

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.global.DropdownItemList
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

    val domains = DropdownItemList.domains
    val localPart = MutableLiveData("")
    val domain = MutableLiveData<Pair<String, Int>>()

    fun requestEmail() {
        Timber.tag(TAG).d("requestEmail: ")

        requestProfile {
            profile.postValue(it)
            it.basicInformation?.email?.let { email ->
                if (email.contains("@")) {
                    email.split("@").let { split ->
                        localPart.postValue(split[0])
                        domains.find { pair -> pair.first == split[1] }.run {
                            if (this != null) domain.postValue(this)
                            else domain.postValue(Pair(split[1],
                                domains.last().second))    // 값이 없을 때, 직접 입력 및 그냥 보여주기
                        }
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
                email = "${localPart.value}@${domain.value?.first}",
            )
        )
    }

    companion object {
        private const val TAG = "EditEmailViewModel"
    }
}