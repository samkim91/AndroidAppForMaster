package kr.co.soogong.master.presentation.ui.requirement

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.domain.usecase.requirement.GetCustomerRequestsUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class RequirementViewModel @Inject constructor(
    private val getCustomerRequestsUseCase: GetCustomerRequestsUseCase,
) : BaseViewModel() {
    val customerRequests = MutableLiveData<CustomerRequest>()

    fun getCustomerRequests() {
        Timber.tag(TAG).d("getCustomerRequests: ")

        getCustomerRequestsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("getCustomerRequests onSuccess: $it")
                    customerRequests.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).d("getCustomerRequests onError: ")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "RequirementViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}