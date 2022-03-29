package kr.co.soogong.master.domain.usecase.requirement.repair

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import kr.co.soogong.master.data.repository.RepairRepository
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SaveRepairUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repairRepository: RepairRepository,
) {
    operator fun invoke(
        repairDto: RepairDto,
        repairImageUri: List<Uri>?,
    ): Single<Requirement> {
        val repair = MultipartGenerator.createJson(repairDto)

        val repairImages = repairImageUri?.let {
            MultipartGenerator.createFiles(context, "images", it)
        }

        return repairRepository.saveRepair(repair, repairImages)
            .map { requirementDto ->
                Requirement.fromRequirementDto(requirementDto)
            }
    }
}