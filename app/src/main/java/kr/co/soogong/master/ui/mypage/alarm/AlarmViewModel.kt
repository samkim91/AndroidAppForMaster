package kr.co.soogong.master.ui.mypage.alarm

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class AlarmViewModel : BaseViewModel() {
    private val _appPushStatus = MutableLiveData(false)
    val appPushStatus: LiveData<Boolean>
        get() = _appPushStatus

    fun clickedAppPush(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("clickedAppPush: $isChecked")
        _appPushStatus.postValue(isChecked)
        setAlarmStatus("push", isChecked)
    }

    private val _kakaoStatus = MutableLiveData(false)
    val kakaoStatus: LiveData<Boolean>
        get() = _kakaoStatus

    fun clickedKakao(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("clickedKakao: $isChecked")
        _kakaoStatus.postValue(isChecked)
        setAlarmStatus("kakao", isChecked)
    }

    private val _smsStatus = MutableLiveData(false)
    val smsStatus: LiveData<Boolean>
        get() = _smsStatus

    fun clickedSMS(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("clickedSMS: $isChecked")
        _smsStatus.postValue(isChecked)
        setAlarmStatus("sms", isChecked)
    }

    fun getAlarmStatus() {
        HttpClient.getAlarmStatus(InjectHelper.keyCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ map ->
                if (map.containsKey("kakao")) {
                    _kakaoStatus.postValue(map["kakao"])
                }
                if (map.containsKey("push")) {
                    _appPushStatus.postValue(map["push"])
                }
                if (map.containsKey("sms")) {
                    _smsStatus.postValue(map["sms"])
                }
            }, {
                Timber.tag(TAG).w("getAlarm: $it")
            })
            .addToDisposable()
    }

    private fun setAlarmStatus(type: String, isChecked: Boolean) {
        HttpClient.setAlarmStatus(InjectHelper.keyCode, type, isChecked)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                Timber.tag(TAG).w("setAlarmStatus: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "AlarmViewModel"
    }
}