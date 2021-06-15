package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.data.dao.profile.ProfileDao
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import javax.inject.Inject

@Reusable
class GetProfileUseCase @Inject constructor(
    private val profileDao: ProfileDao,
    private val profileService: ProfileService,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(): Single<Profile> {
        return Single.just(Profile.NULL_PROFILE)
//        return profileService.getMaster(getMasterUidFromSharedUseCase())
//            .doOnSuccess { master ->
//                // TODO: 2021/06/14 get profile from master
//
////                profileDao.insert(master)
//            }
    }
}