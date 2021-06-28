package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Flowable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetRequirementListUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val requirementDao: RequirementDao,
) {
    operator fun invoke(statusArray: List<String>): Single<List<RequirementCard>> {
        return requirementService.getRequirementList(getMasterIdFromSharedUseCase(), statusArray)
            .doOnSuccess { list ->
                requirementDao.removeByStatus(statusArray)
                requirementDao.insertAll(*list.toTypedArray())
            }
            .map { list ->
                list.map {
                    RequirementCard.fromRequirementDto(it)
                }
            }

        // TODO: 2021/06/21 ㅊㅏ후 repository 패턴을 적용해야함.. 현재는 insert list만 해주고,
        //  detail requirement를 가져올 때만 사용하고 있는데,
        //  list도 디비랑 api랑 같이 가져와서 concat해주는게 좋음.
        //  참고로, maybe의 return이 list이면, switchIfEmpty를 콜하지 못 함. 이유는 emptyList()가 반환되기 때문.
        //  따라서 take(1)를 사용하는 방법이 있으나, 차후 적용
//        return requirementDao.getListByStatus(statusArray)
//            .concatWith(
//                requirementService.getRequirementList(getMasterIdFromSharedUseCase(), statusArray)
//                    .doOnSuccess { list ->
//                        requirementDao.insertAll(*list.toTypedArray())
//                    }
//            )
//            .map { list ->
//                list.map {
//                    RequirementCard.fromRequirementDto(it)
//                }
//            }
    }
}
