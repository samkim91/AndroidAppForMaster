package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Flowable
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.repository.RequirementRepository
import javax.inject.Inject

@Reusable
class GetRequirementUseCase @Inject constructor(
    private val requirementRepository: RequirementRepository,
) {
    operator fun invoke(requirementId: Int): Flowable<RequirementDto> {
        return requirementRepository.getRequirementById(requirementId)
    }
}