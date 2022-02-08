package kr.co.soogong.master.domain.usecase.home

import dagger.Reusable
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.data.network.home.HomeService
import javax.inject.Inject

@Reusable
class GetRequirementTotalUseCase @Inject constructor(
    private val homeService: HomeService,
    private val masterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke() = homeService.getRequirementTotal(masterUidFromSharedUseCase()!!)
}