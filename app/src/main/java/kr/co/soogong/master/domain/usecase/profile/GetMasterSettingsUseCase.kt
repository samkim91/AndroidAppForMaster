package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.domain.entity.profile.MasterSettings
import javax.inject.Inject

@Reusable
class GetMasterSettingsUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(): Single<MasterSettings> =
        profileRepository.getMasterSettings()
}