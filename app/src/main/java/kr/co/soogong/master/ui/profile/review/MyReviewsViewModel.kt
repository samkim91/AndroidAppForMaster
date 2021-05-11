package kr.co.soogong.master.ui.profile.review

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MyReviewsViewModel @Inject constructor(

) : BaseViewModel() {
//    private val _isApprovedMaster = MutableLiveData<Boolean>(getMasterApprovalUseCase())
//    val isApprovedMaster: LiveData<Boolean>
//        get() = _isApprovedMaster


    companion object {
        private const val TAG = "MyReviewsViewModel"
    }
}