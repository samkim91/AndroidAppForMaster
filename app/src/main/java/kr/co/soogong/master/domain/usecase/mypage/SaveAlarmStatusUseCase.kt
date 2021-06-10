package kr.co.soogong.master.domain.usecase.mypage

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.mypage.MyPageService
import javax.inject.Inject

@Reusable
class SaveAlarmStatusUseCase @Inject constructor(
    private val myPageService: MyPageService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(type: String, isChecked: Boolean): Single<JsonObject> {
        return myPageService.setAlarmStatus(getMasterIdFromSharedUseCase(), type, isChecked)
    }
}