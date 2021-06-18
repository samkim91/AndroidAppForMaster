package kr.co.soogong.master.domain.usecase.auth

import com.google.gson.Gson
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.auth.SignUpDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.network.auth.AuthService
import javax.inject.Inject

@Reusable
class SignUpUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(masterDto: MasterDto): Single<MasterDto> {
        return authService.signUp(Gson().toJson(masterDto))
    }
}