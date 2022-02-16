package kr.co.soogong.master.data.datasource.network.profile

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.profile.MasterSettingsDto
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.entity.profile.PortfolioDto
import kr.co.soogong.master.data.entity.requirement.review.ReviewDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ProfileInterface {

    @FormUrlEncoded
    @PATCH(HttpContract.UPDATE_UID_BY_TEL)
    fun updateUidByTel(@Path("tel") tel: String, @Field("uid") uid: String): Single<MasterDto>

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
    @POST(HttpContract.SAVE_PORTFOLIO)
    fun savePortfolio(
        @Part("portfolioDto") portfolioDto: RequestBody,
        @Part beforeImageFile: MultipartBody.Part?,
        @Part afterImageFile: MultipartBody.Part?,
    ): Single<PortfolioDto>

    @GET(HttpContract.GET_PORTFOLIOS)
    fun getPortfolios(
        @Query("uid") uid: String,
        @Query("type") type: String,
        @Query("offset") offset: Int,
        @Query("pageSize") pageSize: Int,
        @Query("order") order: Int,
        @Query("orderBy") orderBy: String,
    ): Single<ResponseDto<PageableContentDto<PortfolioDto>>>

    @DELETE(HttpContract.DELETE_PORTFOLIO)
    fun deletePortfolio(@Path("id") portfolioId: Int): Single<ResponseBody>

    @GET(HttpContract.GET_REVIEWS)
    fun getReviews(
        @Query("uid") uid: String,
        @Query("offset") offset: Int,
        @Query("pageSize") pageSize: Int,
        @Query("order") order: Int,
        @Query("orderBy") orderBy: String,
    ): Single<ResponseDto<PageableContentDto<ReviewDto>>>

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
    fun updateFreeMeasureYn(
        @Body masterDto: MasterDto,
    ): Single<MasterDto>

}