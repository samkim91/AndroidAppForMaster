package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import javax.inject.Inject

@Reusable
class GetPortfolioListUseCase @Inject constructor(
    private val profileService: ProfileService,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val masterDao: MasterDao,
) {
    operator fun invoke(): Single<List<PortfolioDto>> {
        return profileService.getPortfoliosByUid(getMasterUidFromSharedUseCase())
            .doOnSuccess {
//                masterDao.updatePortfolios(getMasterUidFromSharedUseCase(), it)
            }
    }
}