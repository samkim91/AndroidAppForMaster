package kr.co.soogong.master.domain.usecase.mypage

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.mypage.MyPageService
import javax.inject.Inject

@Reusable
class GetAlarmStatusUseCase @Inject constructor(
    private val myPageService: MyPageService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(): Single<HashMap<String, Boolean>> {
        return myPageService.getAlarmStatus(getMasterIdFromSharedUseCase())
    }
}