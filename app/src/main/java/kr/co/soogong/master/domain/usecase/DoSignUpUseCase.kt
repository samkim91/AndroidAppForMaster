package kr.co.soogong.master.domain.usecase

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.network.AuthService
import javax.inject.Inject

@Reusable
class DoSignUpUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(signUpInfo: SignUpInfo): Single<JsonObject> {
        return authService.actionSignUp(signUpInfo)
    }
}