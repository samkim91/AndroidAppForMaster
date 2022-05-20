package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.repository.AuthRepository
import javax.inject.Inject

@Reusable
class RequestCertificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(tel: String): Boolean =
        withContext(Dispatchers.IO) {
            authRepository.requestCertificationCode(mapOf("phoneNumber" to tel))
        }
}