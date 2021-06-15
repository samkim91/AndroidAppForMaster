package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.data.dao.profile.ProfileDao
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import javax.inject.Inject

@Reusable
class GetProfileFromLocalUseCase @Inject constructor(
    private val profileDao: ProfileDao,
    private val getProfileUseCase: GetProfileUseCase,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(): Single<Profile> {
        profileDao.getItem(getMasterUidFromSharedUseCase()).let { profile ->
            if (profile == null) {
                return getProfileUseCase()
            }
            return Single.just(profile)
        }
    }
}