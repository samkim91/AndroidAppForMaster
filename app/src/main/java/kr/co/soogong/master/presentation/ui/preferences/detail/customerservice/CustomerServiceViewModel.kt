package kr.co.soogong.master.presentation.ui.preferences.detail.customerservice

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CustomerServiceViewModel @Inject constructor(

) : BaseViewModel() {

    fun contactViaCall() {
        Timber.tag(TAG).d("contactViaCall: ")
        setAction(VIA_CALL)
    }

    fun contactViaKakao() {
        Timber.tag(TAG).d("contactViaKakao: ")
        setAction(VIA_KAKAO)
    }

    companion object {
        private const val TAG = "CustomerServiceViewModel"
        const val VIA_CALL = "VIA_CALL"
        const val VIA_KAKAO = "VIA_KAKAO"
    }
}