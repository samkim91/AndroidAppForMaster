package kr.co.soogong.master.network.profile

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.profile.MasterDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileInterface {
    @GET(HttpContract.GET_MASTER_BY_UID)
    fun getMasterByUid(@Query("uid") uid: String?): Single<MasterDto>

    @POST(HttpContract.SAVE_MASTER)
    fun saveMaster(@Body masterDto: MasterDto): Single<MasterDto>
}