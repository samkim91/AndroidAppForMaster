package kr.co.soogong.master.data.source.network.profile

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.entity.profile.MasterSettingsDto
import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ProfileInterface {

    @GET(HttpContract.GET_MASTER_SETTINGS)
    fun getMasterSettings(@Path("uid") uid: String): Single<ResponseDto<MasterSettingsDto>>

    @GET(HttpContract.GET_MASTER)
    fun getMasterByUid(@Path("uid") uid: String): Single<ResponseDto<MasterDto>>

    @Multipart
    @POST(HttpContract.SAVE_MASTER)
    fun saveMaster(
        @Part("masterDto") masterDto: RequestBody,
        @Part profileImage: MultipartBody.Part?,
        @Part businessRegistImage: MultipartBody.Part?,
        @Part shopImages: List<MultipartBody.Part?>?,
    ): Single<MasterDto>

    @Multipart
    @PUT(HttpContract.SAVE_PORTFOLIO)
    suspend fun savePortfolio(
        @Part("portfolioDto") savePortfolioJson: RequestBody,
        @Part beforeImageFile: MultipartBody.Part?,
        @Part afterImageFile: MultipartBody.Part?,
    )

    @Multipart
    @PUT(HttpContract.SAVE_REPAIR_PHOTO)
    suspend fun saveRepairPhoto(
        @Part("photoDto") saveRepairPhotoJson: RequestBody,
        @Part imageFiles: List<MultipartBody.Part?>?,
    )

    @Multipart
    @PUT(HttpContract.SAVE_PRICE_BY_PROJECT)
    suspend fun savePriceByProject(
        @Part("priceDto") savePriceByProjectJson: RequestBody,
    )

    @GET(HttpContract.GET_PORTFOLIOS)
    fun getPortfolios(
        @Path("type") type: String,
        @Query("uid") uid: String,
        @Query("offset") offset: Int,
        @Query("pageSize") pageSize: Int,
        @Query("order") order: Int,
        @Query("orderBy") orderBy: String,
    ): Single<ResponseDto<PageableContentDto<PortfolioDto>>>

    @DELETE(HttpContract.DELETE_PORTFOLIO)
    fun deletePortfolio(@Path("id") portfolioId: Int): Single<ResponseBody>

    @FormUrlEncoded
    @PATCH(HttpContract.UPDATE_REQUEST_MEASURE_YN)
    fun updateRequestMeasureYn(
        @Field("uid") uid: String,
    ): Single<MasterDto>

    @PATCH(HttpContract.UPDATE_DIRECT_REPAIR_YN)
    fun updateDirectRepairYn(
        @Body masterDto: MasterDto,
    ): Single<MasterDto>

    @PATCH(HttpContract.UPDATE_FREE_MEASURE_YN)
    suspend fun updateFreeMeasureYn(
        @Body masterDto: MasterDto,
    )

}