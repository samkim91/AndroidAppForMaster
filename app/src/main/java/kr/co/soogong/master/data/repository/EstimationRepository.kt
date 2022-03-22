package kr.co.soogong.master.data.repository

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.source.network.requirement.estimation.EstimationService
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationTemplateDto
import kr.co.soogong.master.data.entity.requirement.estimation.SaveMasterMemoDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@Reusable
class EstimationRepository @Inject constructor(
    private val estimationService: EstimationService,
    // TODO: 2022/03/05 dao 추가
) {

    fun saveEstimation(
        estimationDto: RequestBody,
        estimationImages: List<MultipartBody.Part?>? = null,
    ): Single<EstimationDto> {
        return estimationService.saveEstimation(estimationDto, estimationImages)
    }

    fun respondToMeasure(estimationDto: EstimationDto): Single<EstimationDto> {
        return estimationService.respondToMeasure(estimationDto)
    }

    fun callToClient(data : HashMap<String, Any>): Single<Boolean> = estimationService.callToClient(data)

    fun getCustomerRequests(masterUid: String): Single<CustomerRequest> = estimationService.getCustomerRequests(masterUid)

    fun getEstimationTemplates(masterUid: String): Single<List<EstimationTemplateDto>> {
        return estimationService.getEstimationTemplates(masterUid)
    }

    fun saveEstimationTemplate(estimationTemplateDto: EstimationTemplateDto): Single<EstimationTemplateDto> {
        return estimationService.saveEstimationTemplate(estimationTemplateDto)
    }

    fun deleteEstimationTemplate(id: Int): Single<Boolean> {
        return estimationService.deleteEstimationTemplate(id)
    }

    suspend fun saveMasterMemo(estimationToken: String, masterMemoDto: SaveMasterMemoDto) {
        return estimationService.saveMasterMemo(estimationToken, masterMemoDto)
    }

    companion object {
        private const val TAG = "EstimationRepository"
    }
}
