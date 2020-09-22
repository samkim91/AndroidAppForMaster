package kr.co.soogong.master.ui.settings.alarm

import android.widget.CompoundButton
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber

class AlarmViewModel : BaseViewModel() {
    fun clickedAppPush(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("clickedAppPush: $isChecked")
    }

    fun clickedKakao(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("clickedKakao: $isChecked")
    }

    fun clickedSMS(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("clickedSMS: $isChecked")
    }

    companion object {
        private const val TAG = "AlarmViewModel"
    }
}