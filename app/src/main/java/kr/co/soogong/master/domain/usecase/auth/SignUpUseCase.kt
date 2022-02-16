package kr.co.soogong.master.domain.usecase.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
                // TODO: 2022/02/16 id 추가
                profileRepository.saveMasterKeysInShared(0, it.uid)
            }
            .doOnError {
                Firebase.auth.signOut()
            }
}