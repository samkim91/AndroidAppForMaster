package kr.co.soogong.master.domain.usecase.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SignUpUseCase @Inject constructor(
    private val profileService: ProfileService,
    private val saveMasterBasicDataInSharedUseCase: SaveMasterBasicDataInSharedUseCase,
    private val masterDao: MasterDao,
) {
    operator fun invoke(masterDto: MasterDto): Single<MasterDto> {
        val master = MultipartGenerator.createJson(masterDto)

        return profileService.saveMaster(master).doOnSuccess {
            saveMasterBasicDataInSharedUseCase(it)

//            masterDao.insert(it)
        }.doOnError {
            Firebase.auth.signOut()
        }
    }
}