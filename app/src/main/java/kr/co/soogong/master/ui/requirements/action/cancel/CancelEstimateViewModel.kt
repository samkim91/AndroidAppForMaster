package kr.co.soogong.master.ui.requirements.action.cancel

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CancelEstimateViewModel @Inject constructor(
    private val estimationDao: EstimationDao
) : BaseViewModel() {

    fun doCancel() {

    }
}