package kr.co.soogong.master.ui.mypage.alarm

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.profile.GetMasterUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getMasterUseCase: GetMasterUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _masterDto = MutableLiveData<MasterDto>()

    private val _marketingPush = MutableLiveData(false)
    val marketingPush: LiveData<Boolean>
        get() = _marketingPush

    private val _marketingPushAtNight = MutableLiveData(false)
    val marketingPushAtNight: LiveData<Boolean>
        get() = _marketingPushAtNight

    fun changeMarketingPush(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("changeMarketingPush: $isChecked")
        _marketingPush.postValue(isChecked)
        saveAlarmStatus(MARKETING, isChecked)
    }

    fun changeMarketingPushAtNight(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("changeMarketingPushAtNight: $isChecked")
        _marketingPushAtNight.postValue(isChecked)
        saveAlarmStatus(MARKETING_AT_NIGHT, isChecked)
    }

    fun requestAlarmStatus() {
        getMasterUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ masterDto ->
                Timber.tag(TAG).w("requestAlarmStatus successfully: $masterDto")
                _masterDto.value = masterDto
                _marketingPush.postValue(masterDto.marketingPush)
                _marketingPushAtNight.postValue(masterDto.marketingPushAtNight)
            }, {
                Timber.tag(TAG).w("requestAlarmStatus failed: $it")
            })
            .addToDisposable()
    }

    private fun saveAlarmStatus(type: String, isChecked: Boolean) {
        saveMasterUseCase(
            MasterDto(
                id = _masterDto.value?.id,
                uid = _masterDto.value?.uid,
                marketingPush = if (type == MARKETING) isChecked else null,
                marketingPushAtNight = if (type == MARKETING_AT_NIGHT) isChecked else null,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).w("saveAlarmStatus successfully: $it")
            }, {
                Timber.tag(TAG).w("saveAlarmStatus failed: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "AlarmViewModel"
        private const val MARKETING = "MARKETING"
        private const val MARKETING_AT_NIGHT = "MARKETING_AT_NIGHT"
    }
}