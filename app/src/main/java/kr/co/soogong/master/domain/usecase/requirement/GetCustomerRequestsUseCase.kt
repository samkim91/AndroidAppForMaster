package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.requirement.CustomerRequest
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
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