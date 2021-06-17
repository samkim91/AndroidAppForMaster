package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetRequirementUseCase @Inject constructor(
//    private val getRequirementDao: RequirementDao,
    private val requirementService: RequirementService,
) {
    operator fun invoke(id: Int): Single<RequirementDto> {
        return requirementService.getRequirement(id)
        // TODO: 2021/06/17 Repository pattern 적용 시 수정
//        return Single.just(getRequirementDao.getItem(id))
    }
}