package kr.co.soogong.master.domain.usecase.profile.portfolio

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.entity.profile.portfolio.SavePortfolioDto
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SavePortfolioUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(
        savePortfolioDto: SavePortfolioDto,
        beforeImageUri: Uri?,
        afterImageUri: Uri?,
    ) = withContext(Dispatchers.IO) {
        profileRepository.savePortfolio(
            savePortfolioJson = MultipartGenerator.createJson(savePortfolioDto),
            beforeImageFile = beforeImageUri?.let {
                MultipartGenerator.createFile(context,
                    "beforeImageFile",
                    it)
            },
            afterImageFile = afterImageUri?.let {
                MultipartGenerator.createFile(context,
                    "afterImageFile",
                    it)
            }
        )
    }
}
