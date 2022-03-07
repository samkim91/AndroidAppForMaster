package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.auth.FirebaseTokenDto
import kr.co.soogong.master.data.repository.AuthRepository
import javax.inject.Inject

@Reusable
class SaveFCMTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(token: String): Single<FirebaseTokenDto> {
        return authRepository.saveFCMToken(
            firebaseTokenDto = FirebaseTokenDto(
                personId = getMasterIdFromSharedUseCase(),
                token = token
            )
        )
    }
}