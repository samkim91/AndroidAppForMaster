package kr.co.soogong.master.network

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthInterface {
    @POST(HttpContract.LOGIN)
    fun login(@Body body: HashMap<String, HashMap<String, String>>): Single<JsonObject>

    @POST(HttpContract.FIND)
    fun findInfo(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.SIGN_UP)
    fun signup(@Body body: HashMap<String, HashMap<String, String>>): Single<JsonObject>

    @POST(HttpContract.MASTER_SIGN_UP)
    fun registerMaster(
        @Header("x-authentication-token") token: String,
        @Body body: HashMap<String, String>
    ): Single<JsonObject>

    @POST(HttpContract.PASSWORD)
    fun resetPassword(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.FCM_UPDATE)
    fun updateFCMToken(@Body body: HashMap<String, String?>): Single<Response>
}