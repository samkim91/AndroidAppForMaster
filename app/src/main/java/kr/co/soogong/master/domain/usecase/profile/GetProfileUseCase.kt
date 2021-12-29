package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.Profile
import javax.inject.Inject

@Reusable
class GetProfileUseCase @Inject constructor(
//    private val masterDao: MasterDao,
    private val getMasterUseCase: GetMasterUseCase,
//    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(): Single<Profile> {
        return getMasterUseCase().map { masterDto ->
            Profile.fromMasterDto(masterDto)
        }
//        masterDao.getByUid(getMasterUidFromSharedUseCase())
//            .map { masterDto ->
//                Profile.fromMasterDto(masterDto)
//            }
//            .switchIfEmpty(
//                getMasterUseCase().map { masterDto ->
//                    Profile.fromMasterDto(masterDto)
//                }
//            )
    }
}