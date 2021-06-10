package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.network.auth.AuthService
import javax.inject.Inject

@Reusable
class CheckPhoneNumberExistenceUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(id: String): Single<Response> {
        // TODO: 2021/06/10 Firebase로 찾아오는 과정 필요
        return authService.getId(id)
    }
} 