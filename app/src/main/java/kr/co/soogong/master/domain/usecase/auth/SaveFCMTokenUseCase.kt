package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.entity.auth.FirebaseTokenDto
import kr.co.soogong.master.data.repository.AuthRepository
import javax.inject.Inject

@Reusable
class SaveFCMTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    suspend operator fun invoke(token: String) {
        return withContext(Dispatchers.IO) {
            authRepository.saveFCMToken(
                firebaseTokenDto = FirebaseTokenDto(
                    personId = getMasterIdFromSharedUseCase(),
                    token = token
                )
            )
        }
    }
}