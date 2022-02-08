package kr.co.soogong.master.domain.usecase.requirement

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import kr.co.soogong.master.data.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class RequestReviewUseCase @Inject constructor(
    private val requirementService: RequirementService,
) {
    operator fun invoke(repairDto: RepairDto): Single<JsonObject> {
        return requirementService.requestReview(repairDto)
    }
}