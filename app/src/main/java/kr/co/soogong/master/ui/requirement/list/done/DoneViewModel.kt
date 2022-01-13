package kr.co.soogong.master.ui.requirement.list.done

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.ui.requirement.RequirementViewModelAggregate
import kr.co.soogong.master.ui.requirement.list.RequirementsViewModel
import retrofit2.HttpException
import timber.log.Timber
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltViewModel
class DoneViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
) : RequirementsViewModel(requirementViewModelAggregate) {

    override fun initList() {
        Timber.tag(TAG).d("initList: ")
        requirements.clear()
        resetState()
        requestRequirements()
    }

    override fun loadMoreItems() {
        Timber.tag(TAG).d("loadMoreItems: ")
        requestRequirements()
    }

    override fun requestRequirements() {
        Timber.tag(TAG).d("requestRequirements: ")

        requirementViewModelAggregate.getRequirementCardsUseCase(status = "AfterProcess",
            offset = offset,
            pageSize = pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestRequirements successfully: ")
                    last = it.last
                    totalItemCount += it.numberOfElements
                    requirements.addAll(it.content)
                },
                onError = {
                    Timber.tag(TAG).d("requestRequirements failed: $it")
                    // 인피니티 스크롤에서 데이터가 없는 것은 보여줄 필요가 없기 때문에, 예외처리
                    if ((it as HttpException).code() != HttpURLConnection.HTTP_NOT_FOUND)
                        setAction(REQUEST_FAILED)
                    else isEmptyList.postValue(true)
                }
            ).addToDisposable()

    }

    companion object {
        private const val TAG = "DoneViewModel"
    }
}