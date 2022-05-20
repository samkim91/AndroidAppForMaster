package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.repository.AuthRepository
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.domain.entity.profile.MasterSettings
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
) {
    operator fun invoke(uid: String, tel: String): Single<MasterSettings> {
        return authRepository.signIn(uid, tel).doOnSuccess {
            profileRepository.saveMasterKeysInShared(it.id, it.uid)
        }.map {
            MasterSettings.fromDto(it)
        }
    }
}