package kr.co.soogong.master.presentation.ui.requirement.action.write.template

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.domain.usecase.requirement.DeleteEstimationTemplateUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetEstimationTemplatesUseCase
import kr.co.soogong.master.domain.usecase.requirement.SaveEstimationTemplateUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EstimationTemplatesViewModel @Inject constructor(
    private val getEstimationTemplatesUseCase: GetEstimationTemplatesUseCase,
    private val saveEstimationTemplateUseCase: SaveEstimationTemplateUseCase,
    private val deleteEstimationTemplateUseCase: DeleteEstimationTemplateUseCase,
) : BaseViewModel() {

    private val _estimationTemplates = MutableLiveData<List<EstimationTemplateDto>>()
    val estimationTemplates: LiveData<List<EstimationTemplateDto>>
        get() = _estimationTemplates

    val estimationTemplate = MutableLiveData<EstimationTemplateDto>()

    fun requestEstimationTemplates() {
        Timber.tag(TAG).d("requestEstimationTemplates: ")
        getEstimationTemplatesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestEstimationTemplates successfully: ")
                    _estimationTemplates.value = it
                },
                onError = {
                    Timber.tag(TAG).d("requestEstimationTemplates failed: ")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveEstimationTemplate() {
        Timber.tag(TAG).d("saveEstimationTemplate: ")
        estimationTemplate.value?.let {
            saveEstimationTemplateUseCase(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("saveEstimationTemplate successfully: ")
                        requestEstimationTemplates()
                    },
                    onError = {
                        Timber.tag(TAG).d("saveEstimationTemplate failed: ")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun deleteEstimationTemplate() {
        Timber.tag(TAG).d("deleteEstimationTemplate: ")
        estimationTemplate.value?.id?.let {
            deleteEstimationTemplateUseCase(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("deleteEstimationTemplate successfully: ")
                        requestEstimationTemplates()
                    },
                    onError = {
                        Timber.tag(TAG).d("deleteEstimationTemplate failed: ")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun startAddingTemplate() {
        Timber.tag(TAG).d("startAddingTemplate: ")
        setAction(START_ADDING_TEMPLATE)
    }

    companion object {
        private const val TAG = "EstimationTemplatesViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val START_ADDING_TEMPLATE = "START_ADDING_TEMPLATE"
    }
}