package kr.co.soogong.master.domain.usecase.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.repository.ProfileRepository
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(tel: String, uid: String): Single<MasterDto> {
        return profileRepository.updateUidByTel(tel, uid).doOnSuccess {
            profileRepository.saveMasterKeysInShared(it.id!!, it.uid!!)
        }.doOnError {
            Firebase.auth.signOut()
        }

    }
}