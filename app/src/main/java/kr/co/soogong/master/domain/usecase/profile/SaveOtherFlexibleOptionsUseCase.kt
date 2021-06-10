package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.OtherFlexibleOptions
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class SaveOtherFlexibleOptionsUseCase @Inject constructor(
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(otherFlexibleOptions: OtherFlexibleOptions): Single<Response> {
        return profileService.saveOtherFlexibleOptions(
            getMasterIdFromSharedUseCase()!!,
            otherFlexibleOptions
        )
    }
}