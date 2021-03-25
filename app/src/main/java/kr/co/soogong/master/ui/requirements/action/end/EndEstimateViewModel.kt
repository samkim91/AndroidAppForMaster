package kr.co.soogong.master.ui.requirements.action.end

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class EndEstimateViewModel @Inject constructor(
    private val estimationDao: EstimationDao
) : BaseViewModel() {

    fun doOnFinish() {

    }
}