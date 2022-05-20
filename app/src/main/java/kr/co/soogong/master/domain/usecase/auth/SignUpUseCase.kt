package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.auth.MasterSignUpDto
import kr.co.soogong.master.data.repository.AuthRepository
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.domain.entity.auth.MasterSignUp
import javax.inject.Inject

@Reusable
class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(masterSignUpDto: MasterSignUpDto): Single<MasterSignUp> =
        authRepository.signUp(masterSignUpDto)
            .doOnSuccess {
                profileRepository.saveMasterKeysInShared(it.id, it.uid)
            }
}