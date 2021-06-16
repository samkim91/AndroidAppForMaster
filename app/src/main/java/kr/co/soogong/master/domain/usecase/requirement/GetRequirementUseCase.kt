package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import javax.inject.Inject

@Reusable
class GetRequirementUseCase @Inject constructor(
    private val getRequirementDao: RequirementDao,
) {
    operator fun invoke(id: Int): Single<RequirementDto> {
        return getRequirementDao.getItem(id)
    }
}