package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.datasource.network.profile.ProfileService
import javax.inject.Inject

@Reusable
class UpdateRequestMeasureYnUseCase @Inject constructor(
    private val profileService: ProfileService,
) {
    // TODO: 2022/02/16 repository 를 생성자로 가져와서, 작업하도록 변경 필요 !!

    operator fun invoke(
        uid: String,
    ): Single<MasterDto> {
        return profileService.updateRequestMeasureYn(uid)
//            .doOnSuccess {
//                masterDao.update(it)
//            }
    }
}