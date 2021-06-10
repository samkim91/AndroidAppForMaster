package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.profile.Profile
import kr.co.soogong.master.domain.profile.ProfileDao
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import javax.inject.Inject

@Reusable
class GetProfileFromLocalUseCase @Inject constructor(
    private val profileDao: ProfileDao,
    private val getProfileUseCase: GetProfileUseCase,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(): Single<Profile> {
        profileDao.getItem(getMasterIdFromSharedUseCase()).let { profile ->
            if (profile == null) {
                return getProfileUseCase()
            }
            return Single.just(profile)
        }
    }
}