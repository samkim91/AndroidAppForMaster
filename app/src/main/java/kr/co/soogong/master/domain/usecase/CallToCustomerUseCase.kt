package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.estimation.CancelEstimate
import kr.co.soogong.master.data.requirements.EstimationMessage
import kr.co.soogong.master.network.EstimationsService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class CallToCustomerUseCase @Inject constructor(
    private val estimationsService: EstimationsService
){
    operator fun invoke(keycode : String, date: String): Single<Response> {
        return estimationsService.callToCustomer(keycode, date)
    }
}