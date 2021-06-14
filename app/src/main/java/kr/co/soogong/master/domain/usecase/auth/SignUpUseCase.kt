package kr.co.soogong.master.domain.usecase.auth

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.auth.SignUpDto
import kr.co.soogong.master.network.auth.AuthService
import javax.inject.Inject

@Reusable
class SignUpUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(signUpDto: SignUpDto): Single<JsonObject> {
        // TODO: 2021/06/10 return type 수정
        return authService.signUp(signUpDto)
    }
}