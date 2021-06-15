package kr.co.soogong.master.domain.usecase.mypage

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.mypage.MyPageService
import javax.inject.Inject

@Reusable
class SaveAlarmStatusUseCase @Inject constructor(
    private val myPageService: MyPageService,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(type: String, isChecked: Boolean): Single<JsonObject> {
        return myPageService.setAlarmStatus(getMasterUidFromSharedUseCase(), type, isChecked)
    }
}