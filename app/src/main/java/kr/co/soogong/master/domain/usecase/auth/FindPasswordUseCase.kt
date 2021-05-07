package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class FindPasswordUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(email: String?) : Single<Response> {
        return authService.findPassword(email)
    }
}