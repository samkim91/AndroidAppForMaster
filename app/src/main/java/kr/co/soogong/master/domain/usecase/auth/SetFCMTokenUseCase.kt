package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.network.auth.AuthService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class SetFCMTokenUseCase @Inject constructor(
    private val authService: AuthService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(token: String?): Single<Response> {
        return authService.updateFCMToken(getMasterIdFromSharedUseCase(), fcmKey = token)
    }
}