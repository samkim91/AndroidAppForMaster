package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.requirements.EstimationMessage
import kr.co.soogong.master.network.EstimationsService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SendEstimationMessageUseCase @Inject constructor(
    private val estimationsService: EstimationsService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
){
    operator fun invoke(actionType: String, keycode : String, transmissionType: String, estimationMessage: EstimationMessage): Single<Response> {
        return estimationsService.sendMessage(actionType, getMasterKeyCodeUseCase(), keycode, transmissionType, estimationMessage)
    }

}