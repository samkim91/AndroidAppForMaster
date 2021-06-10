package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.profile.Profile
import kr.co.soogong.master.domain.profile.ProfileDao
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.ProfileService
import javax.inject.Inject

@Reusable
class GetProfileUseCase @Inject constructor(
    private val profileDao: ProfileDao,
    private val profileService: ProfileService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(): Single<Profile> {
        // Todo.. 서버 리뉴얼 후 수정해야함..
//        if (BuildConfig.DEBUG) {
//            return Single.just(Profile.TEST_PROFILE)
//        }

        return profileService.getProfile(getMasterIdFromSharedUseCase())
            .doOnSuccess { profileData ->
                profileDao.insert(profileData)
            }
    }
}