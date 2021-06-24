package kr.co.soogong.master.network.auth

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.profile.MasterDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthInterface {

    @GET(HttpContract.GET_MASTER_BY_UID)
    fun signIn(@Query("uid") uid: String): Single<MasterDto>

    @POST(HttpContract.SAVE_MASTER)
    fun signUp(@Body masterDto: MasterDto): Single<MasterDto>

    @GET(HttpContract.IS_USER_EXIST)
    fun checkUserExistent(@Query("id") id: String): Single<Boolean>

    @POST(HttpContract.FCM_UPDATE)
    fun updateFCMToken(@Body body: HashMap<String, String?>): Single<Response>

}