package kr.co.soogong.master.presentation.ui.profile.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
open class EditProfileContainerViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        private const val TAG = "EditProfileContainerViewModel"
    }
}