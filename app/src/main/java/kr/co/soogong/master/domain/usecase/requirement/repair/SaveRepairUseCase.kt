package kr.co.soogong.master.domain.usecase.requirement.repair

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.entity.requirement.repair.SaveRepairDto
import kr.co.soogong.master.data.repository.RepairRepository
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SaveRepairUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repairRepository: RepairRepository,
) {
    suspend operator fun invoke(
        saveRepairDto: SaveRepairDto,
        repairImageUri: List<Uri>?,
    ) = withContext(Dispatchers.IO) {
        val repair = MultipartGenerator.createJson(saveRepairDto)

        val repairImages = repairImageUri?.let {
            MultipartGenerator.createFiles(context, "images", it)
        }

        repairRepository.saveRepair(repair, repairImages)
    }
}