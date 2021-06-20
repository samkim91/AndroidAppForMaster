package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetRequirementUseCase @Inject constructor(
    private val requirementDao: RequirementDao,
    private val requirementService: RequirementService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(requirementId: Int): Single<RequirementDto> {
        return requirementDao.getItem(requirementId).switchIfEmpty(
            requirementService.getRequirement(
                requirementId,
                getMasterIdFromSharedUseCase()
            )
        ).doOnSuccess {
            requirementDao.insert(it)
        }
    }
}