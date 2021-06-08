package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.auth.SignUpDto
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SignUpUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(signUpDto: SignUpDto): Single<Response> {
        return authService.signUp(signUpDto)
    }
}