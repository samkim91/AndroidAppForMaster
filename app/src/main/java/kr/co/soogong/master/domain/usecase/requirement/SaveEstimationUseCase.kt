package kr.co.soogong.master.domain.usecase.requirement

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import kr.co.soogong.master.data.local.requirement.RequirementDao
import kr.co.soogong.master.data.network.requirement.RequirementService
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SaveEstimationUseCase @Inject constructor(
    private val requirementDao: RequirementDao,
    private val requirementService: RequirementService,
    @ApplicationContext private val context: Context,
) {
    operator fun invoke(estimationDto: EstimationDto, imageUris: List<Uri>?): Single<EstimationDto> {
        val estimation = MultipartGenerator.createJson(estimationDto)

        val images = MultipartGenerator.createFiles(context, "images", imageUris)

        return requirementService.saveEstimation(estimation, images)
//            .doOnSuccess {
//                requirementDao.updateEstimation(requirementId = it.requirementId, it)
//            }
    }
}