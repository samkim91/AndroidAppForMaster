package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetRequirementListUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val requirementDao: RequirementDao,
) {
    operator fun invoke(page: String): Single<List<RequirementDto>> {
        val statusArray = when(page) {
            "Received" -> listOf("Requested", "Estimated")
            "Progress" -> listOf("Repairing", "RequestFinish")
            else -> listOf("Done", "Closed", "CanceledByClient", "CanceledByMaster", "Canceled", "Impossible")
        }

        return requirementService.getRequirementList(getMasterIdFromSharedUseCase(), statusArray)
        // TODO: 2021/06/17 method kotlin.jvm.internal.Intrinsics.checkNotNullParameter 이 발생하고 있음
//            .doOnSuccess { list ->
//                requirementDao.insertAll(*list.toTypedArray())
//            }
    }
}
