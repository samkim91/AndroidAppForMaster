package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.requirements.EstimationMessage
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.EstimationsService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SendEstimationMessageUseCase @Inject constructor(
    private val estimationsService: EstimationsService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
){
    operator fun invoke(keycode : String, transmissionType: String, estimationMessage: EstimationMessage): Single<Response> {
        return estimationsService.sendMessage(getMasterKeyCodeUseCase(), keycode, transmissionType, estimationMessage)
    }
}