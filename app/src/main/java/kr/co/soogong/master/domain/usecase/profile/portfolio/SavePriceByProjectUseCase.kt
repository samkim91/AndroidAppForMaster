package kr.co.soogong.master.domain.usecase.profile.portfolio

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.entity.profile.portfolio.SavePriceByProjectDto
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SavePriceByProjectUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(
        savePriceByProjectDto: SavePriceByProjectDto,
    ) = withContext(Dispatchers.IO) {
        profileRepository.savePriceByProject(
            savePriceByProjectJson = MultipartGenerator.createJson(savePriceByProjectDto),
        )
    }
}
