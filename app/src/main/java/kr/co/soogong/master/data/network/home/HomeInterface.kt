package kr.co.soogong.master.data.network.home

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.home.RequirementTotalDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeInterface {

    @GET(HttpContract.REQUIREMENT_TOTAL)
    fun getRequirementTotal(@Query("masterUid") masterUid: String): Single<ResponseDto<RequirementTotalDto>>
}