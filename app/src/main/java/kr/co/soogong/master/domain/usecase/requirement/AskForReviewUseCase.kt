package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdUseCase
import kr.co.soogong.master.network.EstimationsService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class AskForReviewUseCase @Inject constructor(
    private val estimationsService: EstimationsService,
    private val getMasterIdUseCase: GetMasterIdUseCase
) {
    operator fun invoke(keycode: String): Single<Response> {
        return estimationsService.askForReview(keycode, getMasterIdUseCase())
    }
}