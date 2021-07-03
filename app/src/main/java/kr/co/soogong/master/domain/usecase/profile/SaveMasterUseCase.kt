package kr.co.soogong.master.domain.usecase.profile

import android.net.Uri
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SaveMasterUseCase @Inject constructor(
    private val profileService: ProfileService,
    private val masterDao: MasterDao,
) {
    operator fun invoke(
        masterDto: MasterDto,
        profileImageUri: Uri? = null,
        businessRegistImageUri: Uri? = null,
        thumbnailsUris: List<Uri>? = null
    ): Single<MasterDto> {
//        val master = MultipartGenerator.createJson(masterDto)

        val profileImage = profileImageUri?.let {
            MultipartGenerator.createFile("profileImage", it)
        }

        val businessRegistImage = businessRegistImageUri?.let {
            MultipartGenerator.createFile("businessRegistImage", it)
        }

        val thumbnails = thumbnailsUris?.let {
            MultipartGenerator.createFiles("shopImages", it)
        }

        return profileService.saveMaster(masterDto, profileImage, businessRegistImage, thumbnails)
            .doOnSuccess {
                masterDao.insert(it)
            }
    }
}