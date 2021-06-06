package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.data.profile.OtherFlexibleOptions
import kr.co.soogong.master.domain.usecase.auth.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.ProfileService
import javax.inject.Inject

@Reusable
class GetOtherFlexibleOptionsUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,

    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
    private val profileService: ProfileService,
) {
    suspend operator fun invoke(): OtherFlexibleOptions {
        getProfileFromLocalUseCase().let { profile ->
            return profile.basicInformation?.otherFlexibleOptions ?: OtherFlexibleOptions.NULL_OTHER_FLEXIBLE_OPTIONS
        }
    }
}