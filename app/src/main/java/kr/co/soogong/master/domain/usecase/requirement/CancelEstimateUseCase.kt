package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.requirement.CancelEstimate
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class CancelEstimateUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase
){
    operator fun invoke(keycode : String, cancelEstimate: CancelEstimate): Single<Response> {
        return requirementService.cancelEstimate(getMasterIdFromSharedUseCase(), keycode, cancelEstimate)
    }
}