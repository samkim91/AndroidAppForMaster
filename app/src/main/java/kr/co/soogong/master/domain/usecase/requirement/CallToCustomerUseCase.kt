package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.network.requirement.RequirementService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class CallToCustomerUseCase @Inject constructor(
    private val requirementService: RequirementService
){
    operator fun invoke(id : Int): Single<Response> {
        return requirementService.callToCustomer(id)
    }
}