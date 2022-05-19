package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.repository.AuthRepository
import javax.inject.Inject

@Reusable
class VerifyCertificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(tel: String, code: String): Boolean =
        withContext(Dispatchers.IO) {
            authRepository.verifyCertificationCode(listOf("phoneNumber" to tel, "certificationNumber" to code))
        }
}