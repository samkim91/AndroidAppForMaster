package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.requirement.EstimationMessage
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class SendEstimationMessageUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase
){
    operator fun invoke(keycode : String, transmissionType: String, estimationMessage: EstimationMessage): Single<Response> {
        return requirementService.sendMessage(getMasterUidFromSharedUseCase(), keycode, transmissionType, estimationMessage)
    }
}