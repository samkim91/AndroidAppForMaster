package kr.co.soogong.master.presentation.ui.requirement.action.write

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.entity.requirement.estimation.VisitingDateUpdateDto
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.estimation.UpdateVisitingDateUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.presentation.uihelper.requirment.action.MeasureActivityHelper
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MeasurementViewModel @Inject constructor(
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val updateVisitingDateUseCase: UpdateVisitingDateUseCase,
    private val getRequirementUseCase: GetRequirementUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    private val requirementId =
        MeasureActivityHelper.getRequirementIdFromSavedState(savedStateHandle)

    private val _requirement = MutableLiveData<Requirement>()
    val requirement: LiveData<Requirement>
        get() = _requirement

    val date = MutableLiveData<Calendar>()
    val time = MutableLiveData<Calendar>()

    val description = MutableLiveData("")
    val isSavingTemplate = MutableLiveData(false)

    init {
        requestRequirement()
    }

    private fun requestRequirement() {
        Timber.tag(TAG).d("requestRequirement: $requirementId")
        getRequirementUseCase(requirementId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _requirement.value = it
                },
                onError = {
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun updateVisitingDate() {
        Timber.tag(TAG).d("sendEstimation: ")

        viewModelScope.launch {
            try {
                updateVisitingDateUseCase(
                    estimationToken = _requirement.value?.estimation?.token!!,
                    visitingDateUpdateDto = VisitingDateUpdateDto(
                        masterUid = getMasterUidFromSharedUseCase(),
                        visitingDate = getDatetime()
                    )
                )
                Timber.tag(TAG).d("updateVisitingDate successfully: ${date.value?.time}")
                setAction(UPDATE_VISITING_DATE_SUCCESSFULLY)
            } catch (e: Exception) {
                Timber.tag(TAG).e("updateVisitingDate failed: $e")
                setAction(REQUEST_FAILED)
            }
        }
    }

    private fun getDatetime(): Date {
        date.value?.set(Calendar.HOUR_OF_DAY, time.value?.get(Calendar.HOUR_OF_DAY)!!)
        date.value?.set(Calendar.MINUTE, time.value?.get(Calendar.MINUTE)!!)

        return date.value?.time!!
    }

    fun startEstimationTemplate() {
        Timber.tag(TAG).d("startEstimationTemplate: ")
        setAction(START_ESTIMATION_TEMPLATE)
    }

    fun startViewRequirement() {
        Timber.tag(TAG).d("startViewRequirement: ")
        setAction(START_VIEW_REQUIREMENT)
    }

    fun startDatePicker() {
        Timber.tag(TAG).d("startDatePicker: ")
        setAction(START_DATE_PICKER)
    }

    fun startTimePicker() {
        Timber.tag(TAG).d("startTimePicker: ")
        setAction(START_TIME_PICKER)
    }

    companion object {
        private val TAG = MeasurementViewModel::class.java.simpleName

        const val UPDATE_VISITING_DATE_SUCCESSFULLY = "UPDATE_VISITING_DATE_SUCCESSFULLY"
        const val START_ESTIMATION_TEMPLATE = "START_ESTIMATION_TEMPLATE"
        const val START_VIEW_REQUIREMENT = "START_VIEW_REQUIREMENT"
        const val START_DATE_PICKER = "START_DATE_PICKER"
        const val START_TIME_PICKER = "START_TIME_PICKER"
    }
}