package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.network.profile.ProfileService
import javax.inject.Inject

@Reusable
class UpdateDirectRepairYnUseCase @Inject constructor(
    private val profileService: ProfileService,
    private val masterDao: MasterDao,
) {
    operator fun invoke(
        masterDto: MasterDto,
    ): Single<MasterDto> {
        return profileService.updateDirectRepairYn(masterDto)
//            .doOnSuccess {
//                masterDao.update(it)
//            }
    }
}