package kr.co.soogong.master.domain.usecase.profile

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.source.network.profile.ProfileService
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SaveMasterUseCase @Inject constructor(
    private val profileService: ProfileService,
    @ApplicationContext private val context: Context,
) {
    // TODO: 2022/02/16 repository 를 생성자로 가져와서, 작업하도록 변경 필요 !!

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