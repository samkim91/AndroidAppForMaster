package kr.co.soogong.master.data.source.network.requirement.estimation

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationTemplateDto
import kr.co.soogong.master.data.entity.requirement.estimation.SaveMasterMemoDto
import kr.co.soogong.master.data.entity.requirement.estimation.VisitingDateUpdateDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface EstimationInterface {

    @Multipart
    @POST(HttpContract.SAVE_ESTIMATION)
    fun saveEstimation(
        @Part("estimationDto") estimationDto: RequestBody,
        @Part measurementImage: List<MultipartBody.Part?>?,
    ): Single<EstimationDto>

    @POST(HttpContract.RESPOND_TO_MEASURE)
    fun respondToMeasure(@Body estimationDto: EstimationDto): Single<EstimationDto>

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