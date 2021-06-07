package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.estimation.CancelEstimate
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdUseCase
import kr.co.soogong.master.network.EstimationsService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class CancelEstimateUseCase @Inject constructor(
    private val estimationsService: EstimationsService,
    private val getMasterIdUseCase: GetMasterIdUseCase
){
    operator fun invoke(keycode : String, cancelEstimate: CancelEstimate): Single<Response> {
        return estimationsService.cancelEstimate(getMasterIdUseCase(), keycode, cancelEstimate)
    }
}