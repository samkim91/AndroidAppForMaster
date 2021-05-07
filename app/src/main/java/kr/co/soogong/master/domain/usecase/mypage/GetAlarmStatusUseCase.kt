package kr.co.soogong.master.domain.usecase.mypage

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.AlarmService
import javax.inject.Inject

@Reusable
class GetAlarmStatusUseCase @Inject constructor(
    private val alarmService: AlarmService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
) {
    operator fun invoke(): Single<HashMap<String, Boolean>> {
        return alarmService.getAlarmStatus(getMasterKeyCodeUseCase())
    }
}