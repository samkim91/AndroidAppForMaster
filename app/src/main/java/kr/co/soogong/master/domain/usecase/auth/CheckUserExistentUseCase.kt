package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.network.auth.AuthService
import javax.inject.Inject

@Reusable
class CheckUserExistentUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(id: String): Single<Boolean> {
        return authService.checkUserExistent(id)
    }
} 