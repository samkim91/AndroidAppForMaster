package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.model.profile.RequiredInformation
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import javax.inject.Inject

@Reusable
class GetRequiredInformationUseCase @Inject constructor(
    private val getMasterUseCase: GetMasterUseCase,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val masterDao: MasterDao,
) {
    operator fun invoke(): Single<RequiredInformation> {
        masterDao.getItemByUid(getMasterUidFromSharedUseCase()!!).let { masterDto ->
            if (masterDto == null) {
                return getMasterUseCase().map { response ->
                    RequiredInformation.fromMasterDto(response)
                }
            }
            return Single.just(RequiredInformation.fromMasterDto(masterDto))
        }
    }
}