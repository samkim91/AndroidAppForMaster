package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.verify
import kr.co.soogong.master.data.repository.RequirementRepository
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import javax.inject.Inject

@Reusable
class GetRequirementUseCase @Inject constructor(
    private val requirementRepository: RequirementRepository,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(requirementId: Int): Single<Requirement> {
        return requirementRepository.getRequirementById(requirementId,
            getMasterUidFromSharedUseCase())
            .map { responseDto ->
                Requirement.fromRequirementDto(responseDto.verify()!!)
            }
    }
}