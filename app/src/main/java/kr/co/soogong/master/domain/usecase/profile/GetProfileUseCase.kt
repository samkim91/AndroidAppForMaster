package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import javax.inject.Inject

@Reusable
class GetProfileUseCase @Inject constructor(
    private val masterDao: MasterDao,
    private val getMasterUseCase: GetMasterUseCase,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(): Single<Profile> {
        masterDao.getItemByUid(getMasterUidFromSharedUseCase()!!).let { masterDto ->
            if (masterDto == null) {
                return getMasterUseCase().map { response ->
                    Profile.fromMasterDto(response)
                }
            }
            return Single.just(Profile.fromMasterDto(masterDto))
        }
    }
}