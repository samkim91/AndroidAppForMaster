package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.entity.profile.Profile
import kr.co.soogong.master.data.repository.ProfileRepository
import javax.inject.Inject

@Reusable
class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(): Single<Profile> =
        profileRepository.getMaster().map { Profile.fromMasterDto(it) }
}