package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.data.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetCustomerRequestsUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val masterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(): Single<CustomerRequest> {
        return requirementService.getCustomerRequests(masterUidFromSharedUseCase() ?: "")
    }
}