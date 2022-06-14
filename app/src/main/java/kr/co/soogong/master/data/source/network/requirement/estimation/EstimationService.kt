package kr.co.soogong.master.data.source.network.requirement.estimation

import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.data.entity.requirement.estimation.*
import retrofit2.Retrofit
import javax.inject.Inject

class EstimationService @Inject constructor(
    retrofit: Retrofit,
) {
    private val estimationInterface = retrofit.create(EstimationInterface::class.java)

    fun acceptToMeasure(acceptingMeasureDto: AcceptingMeasureDto): Single<EstimationDto> {
        return estimationInterface.acceptToMeasure(acceptingMeasureDto)
    }

    fun refuseToMeasure(refusingMeasureDto: RefusingMeasureDto): Single<EstimationDto> {
        return estimationInterface.refuseToMeasure(refusingMeasureDto)
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

    suspend fun updateVisitingDate(estimationToken: String, visitingDateUpdateDto: VisitingDateUpdateDto) {
        return estimationInterface.updateVisitingDate(estimationToken, visitingDateUpdateDto)
    }
}
