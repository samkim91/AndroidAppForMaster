package kr.co.soogong.master.data.datasource.network.requirement.estimation

import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationTemplateDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject

class EstimationService @Inject constructor(
    retrofit: Retrofit,
) {
    private val estimationService = retrofit.create(EstimationInterface::class.java)

    fun saveEstimation(
        estimationDto: RequestBody,
        measurementImage: List<MultipartBody.Part?>? = null,
    ): Single<EstimationDto> {
        return estimationService.saveEstimation(estimationDto, measurementImage)
    }

    fun respondToMeasure(estimationDto: EstimationDto): Single<EstimationDto> {
        return estimationService.respondToMeasure(estimationDto)
    }

    fun callToClient(data: HashMap<String, Any>): Single<Boolean> {
        return estimationService.callToClient(data)
    }

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
}
