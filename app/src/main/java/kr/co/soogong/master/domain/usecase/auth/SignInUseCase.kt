package kr.co.soogong.master.domain.usecase.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.network.profile.ProfileService
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val profileService: ProfileService,
    private val saveMasterBasicDataInSharedUseCase: SaveMasterBasicDataInSharedUseCase,
) {
    operator fun invoke(tel: String, uid: String): Single<MasterDto> {
        return profileService.updateUidByTel(tel, uid).doOnSuccess {
            saveMasterBasicDataInSharedUseCase(it)
        }.doOnError {
            Firebase.auth.signOut()
        }
//        return authService.signIn(uid).doOnSuccess {
//            saveMasterBasicDataInSharedUseCase(it)
//
//            masterDao.insert(it)
//        }.doOnError {
//            Firebase.auth.signOut()
//        }
    }
}