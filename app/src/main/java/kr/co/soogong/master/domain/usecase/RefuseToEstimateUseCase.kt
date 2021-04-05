package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.network.EstimationsService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class RefuseToEstimateUseCase @Inject constructor(
    private val estimationsService: EstimationsService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
){
    operator fun invoke(keycode : String): Single<String> {
        return estimationsService.refuseToEstimate(getMasterKeyCodeUseCase(), keycode)
    }

}