package kr.co.soogong.master.domain.usecase.requirement.repair

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.entity.requirement.repair.CancelRepairDto
import kr.co.soogong.master.data.repository.RepairRepository
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class CancelRepairUseCase @Inject constructor(
    private val repairRepository: RepairRepository,
) {
    suspend operator fun invoke(
        cancelRepairDto: CancelRepairDto,
    ) = withContext(Dispatchers.IO) {
        val cancelRepairDtoJson = MultipartGenerator.createJson(cancelRepairDto)

        repairRepository.cancelRepair(cancelRepairDtoJson)
    }
}