package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.repository.AuthRepository
import javax.inject.Inject

@Reusable
class CheckMasterExistentUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(tel: String): Single<Boolean> =
        authRepository.isMasterExistent(tel)
} 