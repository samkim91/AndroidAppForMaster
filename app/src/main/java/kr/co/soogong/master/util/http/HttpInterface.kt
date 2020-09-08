package kr.co.soogong.master.util.http

import io.reactivex.Single
import kr.co.soogong.master.data.rawtype.RawRequirementItem
import retrofit2.http.GET
import retrofit2.http.Query

interface HttpInterface {
    @GET("v1/transmissions/list")
    fun getRequirementList(@Query("branch_keycode") auth: String): Single<List<RawRequirementItem>>
}