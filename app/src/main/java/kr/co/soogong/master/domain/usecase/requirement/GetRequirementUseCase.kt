package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.requirement.Requirement
import kr.co.soogong.master.data.repository.RequirementRepository
import javax.inject.Inject

@Reusable
class GetRequirementUseCase @Inject constructor(
    private val requirementRepository: RequirementRepository,
) {
    operator fun invoke(requirementId: Int): Single<Requirement> {
        return requirementRepository.getRequirementById(requirementId)
            .map { requirementDto ->
                Requirement.fromRequirementDto(requirementDto)
            }
    }
}