package kr.co.soogong.master.domain.usecase.requirement

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import kr.co.soogong.master.data.datasource.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class RequestReviewUseCase @Inject constructor(
    private val requirementService: RequirementService,
) {
    // TODO: 2022/02/16 repository 를 생성자로 가져와서, 작업하도록 변경 필요 !!

    operator fun invoke(repairDto: RepairDto): Single<JsonObject> {
        return requirementService.requestReview(repairDto)
    }
}