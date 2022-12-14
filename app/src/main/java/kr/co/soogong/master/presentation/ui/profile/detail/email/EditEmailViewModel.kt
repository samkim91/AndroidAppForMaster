package kr.co.soogong.master.presentation.ui.profile.detail.email

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.entity.common.DropdownItemList
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileViewModel
import javax.inject.Inject

@HiltViewModel
class EditEmailViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileViewModel(getProfileUseCase, saveMasterUseCase) {

    val domains = DropdownItemList.domains
    val localPart = MutableLiveData("")
    val domain = MutableLiveData<Pair<String, Int>>()

    init {
        requestEmail()
    }

    private fun requestEmail() {
        requestProfile {
            it.basicInformation.email?.let { email ->
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