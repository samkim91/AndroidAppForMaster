package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class ChangePasswordUseCase @Inject constructor(
    private val authService: AuthService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase
) {
    operator fun invoke(password: String?, confirmPassword: String?): Single<Response> {
        return Single.just(Response.TEST_RESPONSE)
    }
}