package kr.co.soogong.master.domain.usecase.requirement.estimation

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.repository.EstimationRepository
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SaveEstimationUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val estimationRepository: EstimationRepository,
) {
    operator fun invoke(
        estimationDto: EstimationDto,
        imageUris: List<Uri>?,
    ): Single<EstimationDto> {
        val estimation = MultipartGenerator.createJson(estimationDto)
        val images = imageUris?.let {
            MultipartGenerator.createFiles(context, "images", it)
        }

        return estimationRepository.saveEstimation(estimation, images)
    }
}