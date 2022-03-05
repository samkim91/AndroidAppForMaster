package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.data.datasource.network.requirement.RequirementService
import kr.co.soogong.master.data.repository.EstimationRepository
import javax.inject.Inject

@Reusable
class GetCustomerRequestsUseCase @Inject constructor(
    private val masterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val estimationRepository: EstimationRepository,
) {
    operator fun invoke(): Single<CustomerRequest> {
        return estimationRepository.getCustomerRequests(masterUidFromSharedUseCase())
    }
}