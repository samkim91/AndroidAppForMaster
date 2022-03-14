package kr.co.soogong.master.domain.usecase.profile.portfolio

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.entity.profile.portfolio.SaveRepairPhotoDto
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SaveRepairPhotoUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(
        saveRepairPhotoDto: SaveRepairPhotoDto,
        newImages: List<Uri?>?,
    ) = withContext(Dispatchers.IO) {
        val saveRepairPhotoJson = MultipartGenerator.createJson(saveRepairPhotoDto)
        val imageFiles = MultipartGenerator.createFiles(context, "imageFiles", newImages)

        profileRepository.saveRepairPhoto(saveRepairPhotoJson, imageFiles)
    }
}
