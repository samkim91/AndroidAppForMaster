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
        shopImagesUris: List<Uri>? = null
    ): Single<MasterDto> {
        val master = MultipartGenerator.createJson(masterDto)

        val profileImage =
            if (profileImageUri != null && profileImageUri != Uri.EMPTY)
                MultipartGenerator.createFile("profileImage", profileImageUri)
            else
                null

        val businessRegistImage =
            if (businessRegistImageUri != null && businessRegistImageUri != Uri.EMPTY)
                MultipartGenerator.createFile("businessRegistImage", businessRegistImageUri)
            else
                null

        val shopImages =
            if (!shopImagesUris.isNullOrEmpty())
                MultipartGenerator.createFiles("shopImages", shopImagesUris)
            else
                null

        return profileService.saveMaster(master, profileImage, businessRegistImage, shopImages)
            .doOnSuccess {
                masterDao.insert(it)
            }
    }
}