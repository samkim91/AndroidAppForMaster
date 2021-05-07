package kr.co.soogong.master.domain.usecase.auth

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SignUpUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(signUpInfo: SignUpInfo): Single<Response> {
        return authService.signUp(signUpInfo)
    }
}