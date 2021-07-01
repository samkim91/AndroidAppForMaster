package kr.co.soogong.master.network.profile

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ProfileInterface {
    @GET(HttpContract.GET_MASTER_BY_UID)
    fun getMasterByUid(@Query("uid") uid: String?): Single<MasterDto>

    @POST(HttpContract.SAVE_MASTER)
    fun saveMaster(@Body masterDto: MasterDto): Single<MasterDto>

    @Multipart
    @POST(HttpContract.SAVE_PORTFOLIO)
    fun savePortfolio(
        @Part("portfolioDto") portfolioDto: RequestBody,
        @Part beforeImageFile: MultipartBody.Part?,
        @Part afterImageFile: MultipartBody.Part?,
    ): Single<PortfolioDto>

    @GET(HttpContract.GET_PORTFOLIOS)
    fun getPortfoliosByUid(@Query("uid") uid: String?): Single<List<PortfolioDto>>
}