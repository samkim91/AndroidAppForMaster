package kr.co.soogong.master.data.repository

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.data.entity.requirement.estimation.*
import kr.co.soogong.master.data.source.network.requirement.estimation.EstimationService
import javax.inject.Inject

@Reusable
class EstimationRepository @Inject constructor(
    private val estimationService: EstimationService,
    // TODO: 2022/03/05 dao 추가
) {

    fun acceptToMeasure(acceptingMeasureDto: AcceptingMeasureDto): Single<EstimationDto> {
        return estimationService.acceptToMeasure(acceptingMeasureDto)
    }

    fun refuseToMeasure(refusingMeasureDto: RefusingMeasureDto): Single<EstimationDto> {
        return estimationService.refuseToMeasure(refusingMeasureDto)
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

    suspend fun updateVisitingDate(estimationToken: String, visitingDateUpdateDto: VisitingDateUpdateDto) {
        return estimationService.updateVisitingDate(estimationToken, visitingDateUpdateDto)
    }

    companion object {
        private val TAG = EstimationRepository::class.java.simpleName
    }
}
