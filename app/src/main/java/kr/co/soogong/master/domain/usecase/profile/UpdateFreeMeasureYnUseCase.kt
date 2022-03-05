package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.repository.ProfileRepository
import javax.inject.Inject

@Reusable
class UpdateFreeMeasureYnUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(masterDto: MasterDto) =
        withContext(Dispatchers.IO) {
            profileRepository.updateFreeMeasureYn(masterDto)
        }
}