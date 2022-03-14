package kr.co.soogong.master.domain.usecase.profile.portfolio

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import kr.co.soogong.master.data.entity.profile.portfolio.SavePortfolioDto
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SavePortfolioUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(
        savePortfolioDto: SavePortfolioDto,
        beforeImageUri: Uri?,
        afterImageUri: Uri?,
    ): Single<PortfolioDto> =
        profileRepository.savePortfolio(
            savePortfolioJson = MultipartGenerator.createJson(savePortfolioDto),
            beforeImageFile = MultipartGenerator.createFile(context,
                "beforeImageFile",
                beforeImageUri),
            afterImageFile = MultipartGenerator.createFile(context,
                "afterImageFile",
                afterImageUri)
        )
}
