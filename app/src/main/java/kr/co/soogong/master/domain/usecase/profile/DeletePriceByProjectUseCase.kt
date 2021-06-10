package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class DeletePriceByProjectUseCase @Inject constructor(
    private val masterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(itemId: Int): Single<Response> {
        return profileService.deletePriceByProject(masterIdFromSharedUseCase()!!, itemId)
    }
}