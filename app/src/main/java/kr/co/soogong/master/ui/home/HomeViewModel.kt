package kr.co.soogong.master.ui.home

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.requirement.RequirementViewModelAggregate
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
) : RequirementViewModel(requirementViewModelAggregate) {

    fun initListUnread() {
        Timber.tag(TAG).d("initListUnread: ")
        requirements.clear()
        resetState()
        requestRequirementsUnread()
    }

    fun requestRequirementsUnread() {
        Timber.tag(TAG).d("requestRequirementsUnread: ")

        requirementViewModelAggregate.getRequirementCardsUseCase(
            RequirementStatus.getRequirementStatusFromTabIndex(null, null),
            readYns = false,
            offset = offset,
            pageSize = pageSize,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestRequirementsUnread successfully: ")
                    last = it.last
                    totalItemCount = it.numberOfElements
                    requirements.addAll(it.content)
                },
                onError = {
                    Timber.tag(TAG).d("requestRequirementsUnread failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }


    companion object {
        private const val TAG = "HomeViewModel"

    }
}