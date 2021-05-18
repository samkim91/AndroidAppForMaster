package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class CheckPhoneNumberDuplicateUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(id: String?): Single<Response> {
        return authService.checkIdExist(id)
    }
}