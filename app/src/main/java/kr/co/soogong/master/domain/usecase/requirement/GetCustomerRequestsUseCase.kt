package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.data.datasource.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetCustomerRequestsUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val masterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    // TODO: 2022/02/16 repository 를 생성자로 가져와서, 작업하도록 변경 필요 !!

    operator fun invoke(): Single<CustomerRequest> {
        return requirementService.getCustomerRequests(masterUidFromSharedUseCase())
    }
}