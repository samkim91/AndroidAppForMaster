package kr.co.soogong.master.ui.requirement.received

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.profile.SecretaryCodeTable
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.data.model.requirement.RequirementStatus.Companion.receivedStatus
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.requirement.RequirementViewModelAggregate
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
) : RequirementViewModel(requirementViewModelAggregate) {

    override fun requestList() {
        Timber.tag(TAG).d("requestList: ${filterTabIndex.value}")

        filterTabIndex.value?.let { _index ->
            requirementViewModelAggregate.getRequirementCardsUseCase(
                if (_index == 0 || _index == 2) {
                    receivedStatus.map { it.code }
                } else {
                    listOf(receivedStatus[_index - 1].code)
                }
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("requestList successfully: ")
                        if (_index == 0) sendEvent(BADGE_UPDATE, it.count())
                        when (_index) {
                            1 -> requirements.postValue(it.filter { requirementCard -> requirementCard.estimationDto?.requestConsultingYn == false })
                            2 -> requirements.postValue(it.filter { requirementCard -> requirementCard.estimationDto?.requestConsultingYn == true })
                            3 -> requirements.postValue(it.filter { requirementCard -> requirementCard.typeCode == SecretaryCodeTable.code })
                            4 -> requirements.postValue(it.filter { requirementCard -> requirementCard.status is RequirementStatus.Estimated })
                            else -> requirements.postValue(it)
                        }
                    },
                    onError = {
                        Timber.tag(TAG).d("requestList failed: $it")
                        requirements.postValue(emptyList())
                    },
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "ReceivedViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
    }
}