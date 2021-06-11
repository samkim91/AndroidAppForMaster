package kr.co.soogong.master.network.auth

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthInterface {

    @POST(HttpContract.MASTER_SIGN_IN)
    fun signIn(@Body body: String): Single<JsonObject>

    @GET(HttpContract.IS_USER_EXIST)
    fun checkUserExistent(@Query("id") id: String): Single<Boolean>

    @POST(HttpContract.FCM_UPDATE)
    fun updateFCMToken(@Body body: HashMap<String, String?>): Single<Response>

}