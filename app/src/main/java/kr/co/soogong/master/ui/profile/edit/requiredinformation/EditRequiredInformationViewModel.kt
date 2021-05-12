package kr.co.soogong.master.ui.profile.edit.requiredinformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.profile.MyReview
import kr.co.soogong.master.data.profile.RequiredInformation
import kr.co.soogong.master.domain.usecase.profile.GetMyReviewsUseCase
import kr.co.soogong.master.domain.usecase.profile.GetRequiredInformationUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditRequiredInformationViewModel @Inject constructor(
    private val getRequiredInformationUseCase: GetRequiredInformationUseCase
) : BaseViewModel() {

    private val _requiredInformation = MutableLiveData<RequiredInformation?>()
    val requiredInformation: LiveData<RequiredInformation?>
        get() = _requiredInformation

    fun getRequiredInformation() {
        Timber.tag(TAG).d("getRequiredInformation: ")
        viewModelScope.launch {
            _requiredInformation.value = getRequiredInformationUseCase()
        }
    }

    companion object {
        private const val TAG = "EditRequiredInformationViewModel"
    }
}