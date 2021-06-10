package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.requirement.EstimationsService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class AskForReviewUseCase @Inject constructor(
    private val estimationsService: EstimationsService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase
) {
    operator fun invoke(keycode: String): Single<Response> {
        return estimationsService.askForReview(keycode, getMasterIdFromSharedUseCase())
    }
}