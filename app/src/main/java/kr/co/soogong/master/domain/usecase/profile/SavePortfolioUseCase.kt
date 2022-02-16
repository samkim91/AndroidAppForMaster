package kr.co.soogong.master.domain.usecase.profile

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import kr.co.soogong.master.data.entity.profile.PortfolioDto
import kr.co.soogong.master.data.datasource.network.profile.ProfileService
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SavePortfolioUseCase @Inject constructor(
    private val profileService: ProfileService,
    @ApplicationContext private val context: Context,
) {
    // TODO: 2022/02/16 repository 를 생성자로 가져와서, 작업하도록 변경 필요 !!

    operator fun invoke(portfolio: PortfolioDto, beforeImageUri: Uri?, afterImageUri: Uri?): Single<PortfolioDto> {
        val portfolioDto = MultipartGenerator.createJson(portfolio)
        val beforeImageFile = MultipartGenerator.createFile(context,"beforeImageFile", beforeImageUri)
        val afterImageFile = MultipartGenerator.createFile(context,"afterImageFile", afterImageUri)

        return profileService.savePortfolio(portfolioDto, beforeImageFile, afterImageFile)
    }
}
