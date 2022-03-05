package kr.co.soogong.master.domain.usecase.profile

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import kr.co.soogong.master.data.entity.profile.PortfolioDto
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SavePortfolioUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(
        portfolio: PortfolioDto,
        beforeImageUri: Uri?,
        afterImageUri: Uri?,
    ): Single<PortfolioDto> {
        val portfolioDto = MultipartGenerator.createJson(portfolio)
        val beforeImageFile = MultipartGenerator.createFile(context, "beforeImageFile", beforeImageUri)
        val afterImageFile = MultipartGenerator.createFile(context, "afterImageFile", afterImageUri)

        return profileRepository.savePortfolio(portfolioDto, beforeImageFile, afterImageFile)
    }
}
