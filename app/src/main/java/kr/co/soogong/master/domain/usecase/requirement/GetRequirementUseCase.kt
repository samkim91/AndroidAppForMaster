package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetRequirementUseCase @Inject constructor(
//    private val getRequirementDao: RequirementDao,
    private val requirementService: RequirementService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(requirementId: Int): Single<RequirementDto> {
        return requirementService.getRequirement(requirementId, getMasterIdFromSharedUseCase())
        // TODO: 2021/06/17 Repository pattern 적용 시 수정
//        return Single.just(getRequirementDao.getItem(id))
    }
}