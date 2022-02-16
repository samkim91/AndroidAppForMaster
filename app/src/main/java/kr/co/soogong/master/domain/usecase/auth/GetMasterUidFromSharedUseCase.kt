package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import kr.co.soogong.master.data.repository.ProfileRepository
import javax.inject.Inject

@Reusable
class GetMasterUidFromSharedUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(): String =
        profileRepository.getMasterUidFromShared()
}