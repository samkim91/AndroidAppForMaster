package kr.co.soogong.master.data.datasource.network.requirement.estimation

import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationTemplateDto
import kr.co.soogong.master.data.entity.requirement.estimation.SaveMasterMemoDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject

class EstimationService @Inject constructor(
    retrofit: Retrofit,
) {
    private val estimationInterface = retrofit.create(EstimationInterface::class.java)

    fun saveEstimation(
        estimationDto: RequestBody,
        measurementImage: List<MultipartBody.Part?>? = null,
    ): Single<EstimationDto> {
        return estimationInterface.saveEstimation(estimationDto, measurementImage)
    }

    fun respondToMeasure(estimationDto: EstimationDto): Single<EstimationDto> {
        return estimationInterface.respondToMeasure(estimationDto)
    }

    fun callToClient(data: HashMap<String, Any>): Single<Boolean> {
        return estimationInterface.callToClient(data)
    }

    fun getCustomerRequests(masterUid: String): Single<CustomerRequest> = estimationInterface.getCustomerRequests(masterUid)

    fun getEstimationTemplates(masterUid: String): Single<List<EstimationTemplateDto>> {
        return estimationInterface.getEstimationTemplates(masterUid)
    }

    fun saveEstimationTemplate(estimationTemplateDto: EstimationTemplateDto): Single<EstimationTemplateDto> {
        return estimationInterface.saveEstimationTemplate(estimationTemplateDto)
    }

    fun deleteEstimationTemplate(id: Int): Single<Boolean> {
        return estimationInterface.deleteEstimationTemplate(id)
    }

    suspend fun saveMasterMemo(estimationToken: String, masterMemoDto: SaveMasterMemoDto) {
        return estimationInterface.saveMasterNote(estimationToken, masterMemoDto)
    }
}
