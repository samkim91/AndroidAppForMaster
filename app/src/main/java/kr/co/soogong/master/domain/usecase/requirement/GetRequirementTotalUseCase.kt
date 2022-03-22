package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import kr.co.soogong.master.data.source.network.requirement.RequirementService
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import javax.inject.Inject

@Reusable
class GetRequirementTotalUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val masterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke() = requirementService.getRequirementTotal(masterUidFromSharedUseCase())
}