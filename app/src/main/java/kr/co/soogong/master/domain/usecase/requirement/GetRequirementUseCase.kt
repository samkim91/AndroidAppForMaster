package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Flowable
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
    operator fun invoke(requirementId: Int): Flowable<RequirementDto> {
        return requirementDao.getItem(requirementId).mergeWith(
            requirementService.getRequirement(
                requirementId,
                getMasterIdFromSharedUseCase()
            ).doOnSuccess {
                requirementDao.insert(it)
            }
        )
    }
}