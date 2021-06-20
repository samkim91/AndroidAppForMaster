package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetRequirementListUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val requirementDao: RequirementDao,
) {
    operator fun invoke(statusArray: List<String>): Single<List<RequirementCard>> {
        return requirementDao.getListByStatus(statusArray)
            .switchIfEmpty(requirementService.getRequirementList(getMasterIdFromSharedUseCase(), statusArray))
            .doOnSuccess { list ->
                requirementDao.insertAll(*list.toTypedArray())
            }.map { list ->
                list.map {
                    RequirementCard.fromRequirementDto(it)
                }
            }
    }
}
