package kr.co.soogong.master.domain.usecase.profile

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
) {
    operator fun invoke(
        masterDto: MasterDto,
        profileImageUri: Uri? = null,
        businessRegistImageUri: Uri? = null,
        shopImagesUris: List<Uri>? = null
    ): Single<MasterDto> {
        val master = MultipartGenerator.createJson(masterDto)

        val profileImage = MultipartGenerator.createFile(context, "profileImage", profileImageUri)

        val businessRegistImage =
            MultipartGenerator.createFile(context, "businessRegistImage", businessRegistImageUri)

        val shopImages = MultipartGenerator.createFiles(context, "shopImages", shopImagesUris)

        return profileService.saveMaster(master, profileImage, businessRegistImage, shopImages)
//            .doOnSuccess {
//                masterDao.insert(it)
//            }
    }
}