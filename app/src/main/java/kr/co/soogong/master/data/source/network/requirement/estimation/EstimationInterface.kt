package kr.co.soogong.master.data.source.network.requirement.estimation

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.data.entity.requirement.estimation.*
import retrofit2.http.*

interface EstimationInterface {

    @POST(HttpContract.ACCEPT_TO_MEASURE)
    fun acceptToMeasure(@Body acceptingMeasureDto: AcceptingMeasureDto): Single<EstimationDto>

    @POST(HttpContract.REFUSE_TO_MEASURE)
    fun refuseToMeasure(@Body refusingMeasureDto: RefusingMeasureDto): Single<EstimationDto>

    @POST(HttpContract.CALL_TO_CLIENT)
    fun callToClient(@Body data: HashMap<String, Any>): Single<Boolean>

    @GET(HttpContract.GET_CUSTOMER_REQUESTS)
    fun getCustomerRequests(
        @Query("uid") masterUid: String,
    ): Single<CustomerRequest>

    @GET(HttpContract.GET_ESTIMATION_TEMPLATES)
    fun getEstimationTemplates(@Query("uid") masterUid: String): Single<List<EstimationTemplateDto>>

    @Multipart
    @POST(HttpContract.SAVE_ESTIMATION_TEMPLATE)
    fun saveEstimationTemplate(@Part("estimationTemplateDto") estimationTemplateDto: EstimationTemplateDto): Single<EstimationTemplateDto>

    @DELETE(HttpContract.DELETE_ESTIMATION_TEMPLATE)
    fun deleteEstimationTemplate(@Path("id") id: Int): Single<Boolean>

    @PATCH(HttpContract.SAVE_MASTER_NOTE)
    suspend fun saveMasterNote(@Path("token") estimationToken: String, @Body masterMemoDto: SaveMasterMemoDto)

    @PATCH(HttpContract.UPDATE_VISITING_DATE)
    suspend fun updateVisitingDate(@Path("token") estimationToken: String, @Body visitingDateUpdateDto: VisitingDateUpdateDto)
}