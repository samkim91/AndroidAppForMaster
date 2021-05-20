package kr.co.soogong.master.ui.profile.edit.requiredinformation.businesstypes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.profile.OtherFlexibleOptions
import kr.co.soogong.master.domain.usecase.profile.*
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.utils.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditBusinessTypesViewModel @Inject constructor(
    private val getBusinessTypesUseCase: GetBusinessTypesUseCase,
    private val saveBusinessTypesUseCase: SaveBusinessTypesUseCase,
) : BaseViewModel() {
    val businessTypes = ListLiveData<BusinessType>()

    fun loadBusinessTypes() {
        Timber.tag(TAG).d("loadBusinessTypes: ")
        viewModelScope.launch {
            businessTypes.addAll(getBusinessTypesUseCase())
        }
    }

    fun saveBusinessTypes() {
        Timber.tag(TAG).d("saveBusinessTypes: ")
        businessTypes.value?.let {
            saveBusinessTypesUseCase(
                businessTypes = it
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_BUSINESS_TYPES_SUCCESSFULLY) },
                    onError = { setAction(SAVE_BUSINESS_TYPES_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditBusinessTypesViewModel"
        const val SAVE_BUSINESS_TYPES_SUCCESSFULLY = "SAVE_BUSINESS_TYPES_SUCCESSFULLY"
        const val SAVE_BUSINESS_TYPES_FAILED = "SAVE_BUSINESS_TYPES_FAILED"

    }
}