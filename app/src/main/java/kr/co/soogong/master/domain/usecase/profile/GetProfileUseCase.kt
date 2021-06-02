package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.domain.profile.ProfileDao
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.ProfileService
import kr.co.soogong.master.data.profile.Profile
import javax.inject.Inject

@Reusable
class GetProfileUseCase @Inject constructor(
    private val profileDao: ProfileDao,
    private val profileService: ProfileService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
) {
    suspend operator fun invoke(): Profile {
        // Todo.. 서버 리뉴얼 후 수정해야함..
        if(BuildConfig.DEBUG) {
            return Profile.TEST_PROFILE
        }

        val profileData = profileService.getProfile(getMasterKeyCodeUseCase())
        profileDao.insert(profileData)
        return profileData

    }
}