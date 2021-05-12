package kr.co.soogong.master.ui.profile.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.profile.MyReview
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

    fun getMyReviews() {
        Timber.tag(TAG).d("getMyReviews: ")
        viewModelScope.launch {
            _myReview.value = getMyReviewsUseCase()
        }
    }

    companion object {
        private const val TAG = "MyReviewsViewModel"
    }
}