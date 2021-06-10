package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.network.requirement.EstimationsService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class CallToCustomerUseCase @Inject constructor(
    private val estimationsService: EstimationsService
){
    operator fun invoke(keycode : String, date: String): Single<Response> {
        return estimationsService.callToCustomer(keycode, date)
    }
}