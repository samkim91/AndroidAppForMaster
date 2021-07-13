package kr.co.soogong.master.domain.usecase.requirement

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.network.requirement.RequirementService
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import javax.inject.Inject

@Reusable
class RequestReviewUseCase @Inject constructor(
    private val requirementService: RequirementService,
){
    operator fun invoke(repairDto: RepairDto): Single<JsonObject> {
        return requirementService.requestReview(repairDto)
    }
}