package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.auth.FirebaseTokenDto
import kr.co.soogong.master.data.network.auth.AuthService
import javax.inject.Inject

@Reusable
class SaveFCMTokenUseCase @Inject constructor(
    private val authService: AuthService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(token: String): Single<FirebaseTokenDto> {
        return authService.saveFCMToken(
            firebaseTokenDto = FirebaseTokenDto(
                personId = getMasterIdFromSharedUseCase(),
                token = token
            )
        )
    }
}