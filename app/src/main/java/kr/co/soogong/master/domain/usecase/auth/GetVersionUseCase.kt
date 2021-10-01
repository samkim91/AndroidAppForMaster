package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.auth.VersionDto
import kr.co.soogong.master.network.auth.AuthService
import javax.inject.Inject

@Reusable
class GetVersionUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(): Single<VersionDto> {
        return authService.getVersion()
    }
}