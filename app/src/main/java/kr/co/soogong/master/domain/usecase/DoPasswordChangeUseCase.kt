package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class DoPasswordChangeUseCase @Inject constructor(
    private val authService: AuthService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
) {
    operator fun invoke(password: String?, confirmPassword: String?): Single<Response> {
        return authService.passwordChange(getMasterKeyCodeUseCase(), password, confirmPassword)
    }
}