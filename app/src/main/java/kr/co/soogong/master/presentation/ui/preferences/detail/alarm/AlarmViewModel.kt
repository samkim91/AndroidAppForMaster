package kr.co.soogong.master.presentation.ui.preferences.detail.alarm

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _masterDto = MutableLiveData<MasterDto>()

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
        profileRepository.getMaster()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("requestAlarmStatus successfully: ")
                _masterDto.value = it
                _marketingPush.postValue(it.marketingPush)
                _pushAtNight.postValue(it.pushAtNight)
            }, {
                Timber.tag(TAG).d("requestAlarmStatus failed: $it")
            })
            .addToDisposable()
    }

    fun changeMarketingPush(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("changeMarketingPush: $isChecked")
        _marketingPush.postValue(isChecked)
        saveAlarmStatus(MARKETING, isChecked)
    }

    fun changeMarketingPushAtNight(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("changeMarketingPushAtNight: $isChecked")
        _pushAtNight.postValue(isChecked)
        saveAlarmStatus(MARKETING_AT_NIGHT, isChecked)
    }

    private fun saveAlarmStatus(type: String, isChecked: Boolean) {
        saveMasterUseCase(
            MasterDto(
                id = _masterDto.value?.id,
                uid = _masterDto.value?.uid,
                marketingPush = if (type == MARKETING) isChecked else null,
                pushAtNight = if (type == MARKETING_AT_NIGHT) isChecked else null,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("saveAlarmStatus successfully: ")
            }, {
                Timber.tag(TAG).d("saveAlarmStatus failed: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "AlarmViewModel"
        private const val MARKETING = "MARKETING"
        private const val MARKETING_AT_NIGHT = "MARKETING_AT_NIGHT"
    }
}