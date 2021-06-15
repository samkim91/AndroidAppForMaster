package kr.co.soogong.master.domain.usecase.auth

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.network.auth.AuthService
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(uId: String): Single<JsonObject> {
        // TODO: 2021/06/10 return type 수정


        return authService.signIn(uId)
    }
}