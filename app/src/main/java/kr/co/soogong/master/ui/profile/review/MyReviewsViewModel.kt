package kr.co.soogong.master.ui.profile.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.profile.MyReview
import kr.co.soogong.master.domain.usecase.profile.GetMyReviewsUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyReviewsViewModel @Inject constructor(
    private val getMyReviewsUseCase: GetMyReviewsUseCase
) : BaseViewModel() {

    private val _myReview = MutableLiveData<MyReview?>()
    val myReview: LiveData<MyReview?>
        get() = _myReview

    fun requestMyReviews() {
        Timber.tag(TAG).d("requestMyReviews: ")
        getMyReviewsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _myReview.value = it
                },
                onError = { setAction(GET_MY_REVIEWS_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "MyReviewsViewModel"
        const val GET_MY_REVIEWS_FAILED = "GET_MY_REVIEWS_FAILED"
    }
}