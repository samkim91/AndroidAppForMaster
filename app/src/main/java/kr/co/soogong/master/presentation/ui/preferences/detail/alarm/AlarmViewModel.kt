package kr.co.soogong.master.presentation.ui.preferences.detail.alarm

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.entity.profile.Profile
import kr.co.soogong.master.domain.usecase.preferences.SetMarketingPushUseCase
import kr.co.soogong.master.domain.usecase.preferences.SetPushAtNightUseCase
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val setPushAtNightUseCase: SetPushAtNightUseCase,
    private val setMarketingPushUseCase: SetMarketingPushUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()

    private val _marketingPush = MutableLiveData(false)
    val marketingPush: LiveData<Boolean>
        get() = _marketingPush

    private val _pushAtNight = MutableLiveData(false)
    val pushAtNight: LiveData<Boolean>
        get() = _pushAtNight

    init {
        requestAlarmStatus()
    }

    private fun requestAlarmStatus() {
        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("requestAlarmStatus successfully: ")
                _profile.value = it
                _marketingPush.postValue(it.marketingPush)
                _pushAtNight.postValue(it.pushAtNight)
            }, {
                Timber.tag(TAG).d("requestAlarmStatus failed: $it")
            })
            .addToDisposable()
    }

    fun changeMarketingPush(v: CompoundButton, isChecked: Boolean) {
        viewModelScope.launch {
            try {
                if (_marketingPush.value != isChecked) {
                    setMarketingPushUseCase()
                    _marketingPush.postValue(isChecked)
                    Timber.tag(TAG).d("changeMarketingPush successfully: ")
                }
            } catch (e: Exception) {
                Timber.tag(TAG).d("changeMarketingPush failed: $e")
            }
        }
    }

    fun changePushAtNight(v: CompoundButton, isChecked: Boolean) {
        viewModelScope.launch {
            try {
                if (_pushAtNight.value != isChecked) {
                    setPushAtNightUseCase()
                    _pushAtNight.postValue(isChecked)
                    Timber.tag(TAG).d("changePushAtNight successfully: ")
                }
            } catch (e: Exception) {
                Timber.tag(TAG).d("changePushAtNight failed: $e")
            }
        }
    }

    companion object {
        private const val TAG = "AlarmViewModel"
    }
}