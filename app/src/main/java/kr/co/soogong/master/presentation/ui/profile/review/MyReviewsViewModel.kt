package kr.co.soogong.master.presentation.ui.profile.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.entity.profile.Profile
import kr.co.soogong.master.domain.entity.profile.Review
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.requirement.review.GetReviewsUseCase
import kr.co.soogong.master.presentation.ui.common.EndlessScrollableViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyReviewsViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
) : EndlessScrollableViewModel() {
    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile>
        get() = _profile

    val reviews = ListLiveData<Review>()

    init {
        requestProfile()
        initList()
    }

    override fun initList() {
        Timber.tag(TAG).d("initList: ")
        reviews.clear()
        resetState()
        requestMyReviews()
    }

    override fun loadMoreItems() {
        Timber.tag(TAG).d("loadMoreItems: ")
        requestMyReviews()
    }

    private fun requestMyReviews() {
        Timber.tag(TAG).d("requestMyReviews: ")

        getReviewsUseCase(offset, pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { pageableContentDto ->
                    Timber.tag(TAG).d("requestMyReviews successfully: ")
                    last = pageableContentDto.last
                    totalItemCount += pageableContentDto.numberOfElements
                    reviews.addAll(pageableContentDto.content)
                },
                onError = { setAction(GET_MY_REVIEWS_FAILED) }
            ).addToDisposable()
    }

    private fun requestProfile() {
        Timber.tag(TAG).d("requestProfile: ")
        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestProfile successfully: $it")
                    _profile.value = it
                },
                onError = { setAction(GET_MY_REVIEWS_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "MyReviewsViewModel"
        const val GET_MY_REVIEWS_FAILED = "GET_MY_REVIEWS_FAILED"
    }
}