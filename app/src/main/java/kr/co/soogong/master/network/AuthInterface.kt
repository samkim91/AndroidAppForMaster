package kr.co.soogong.master.network

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthInterface {
    @POST(HttpContract.LOGIN)
    fun login(@Body body: HashMap<String, HashMap<String, String?>>): Single<JsonObject>

    @POST(HttpContract.SIGN_UP)
    fun signUp(@Body body: HashMap<String, HashMap<String, Any>>): Single<Response>

    @POST(HttpContract.MASTER_SIGN_UP)
    fun registerMaster(
        @Header("x-authentication-token") token: String,
        @Body body: HashMap<String, String>
    ): Single<JsonObject>

    @POST(HttpContract.PASSWORD)
    fun passwordChange(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.FCM_UPDATE)
    fun updateFCMToken(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.FIND)
    fun findPassword(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.CHECK_ID_EXIST)
    fun checkIdExist(@Body body: HashMap<String, String?>): Single<Response>
}