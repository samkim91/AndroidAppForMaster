package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SetFCMTokenUseCase @Inject constructor(
    private val authService: AuthService,
    private val getMasterIdUseCase: GetMasterIdUseCase,
) {
    operator fun invoke(token: String?): Single<Response> {
        return authService.updateFCMToken(getMasterIdUseCase(), fcmKey = token)
    }
}