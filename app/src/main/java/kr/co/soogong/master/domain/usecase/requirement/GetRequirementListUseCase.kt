package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Flowable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.dao.estimation.EstimationDao
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import timber.log.Timber
import javax.inject.Inject

@Reusable
class GetRequirementListUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val requirementDao: RequirementDao,
) {
    operator fun invoke(): Single<List<RequirementDto>> {
        return requirementService.getRequirementList(getMasterIdFromSharedUseCase())
            .doOnSuccess { list ->
                requirementDao.insertAll(list)
            }
    }
}