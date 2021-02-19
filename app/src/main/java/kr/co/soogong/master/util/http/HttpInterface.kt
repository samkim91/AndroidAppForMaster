package kr.co.soogong.master.util.http

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.*

interface HttpInterface {
    @POST("login")
    fun login(@Body body: HashMap<String, HashMap<String, String>>): Single<JsonObject>

    @POST("find")
    fun findInfo(@Body body: HashMap<String, String?>): Single<Response>

    @POST("/signup")
    fun signup(@Body body: HashMap<String, HashMap<String, String>>): Single<JsonObject>

    @POST("api/v1/branches/register")
    fun registerMaster(
        @Header("x-authentication-token") token: String,
        @Body body: HashMap<String, String>
    ): Single<JsonObject>

    @GET("api/v1/categories")
    fun getCategoryList(): Single<List<JsonObject>>

    @GET("api/v1/categories/{id}/projects")
    fun getProjectList(@Path("id") id: Int): Single<List<JsonObject>>

    @GET("api/v1/transmissions/default_list")
    fun getRequirementList(@Query("branch_keycode") auth: String?): Single<List<JsonObject>>

    @GET("api/v2/estimations/{branch_keycode}")
    fun getEstimationList(@Path("branch_keycode") branchKeycode: String?) : Single<JsonObject>

    @GET("api/v1/transmissions/accept_list")
    fun getProgressList(@Query("branch_keycode") auth: String?): Single<List<JsonObject>>

    @POST("api/v1/transmissions/refuse")
    fun refuseRequirement(@Body body: HashMap<String, String?>): Single<Response>

    @POST("api/v1/transmissions/send_message")
    fun sendMessage(@Body body: HashMap<String, String?>): Single<String>

    @POST("api/v1/branches/update_reg_id")
    fun updateFCMToken(@Body body: HashMap<String, String?>): Single<Response>

    @GET("api/v1/branches/search/{keycode}")
    fun getUserProfile(@Path("keycode") keycode: String?): Single<JsonObject>

    @GET("api/v1/boards/notice")
    fun getNoticeList(@Query("for") master: String): Single<List<JsonObject>>

    @POST("password")
    fun resetPassword(@Body body: HashMap<String, String?>): Single<Response>

    @POST("api/v1/branches/get_alarm")
    fun getAlarmStatus(@Body body: HashMap<String, String?>): Single<JsonObject>

    @POST("api/v1/branches/set_alarm")
    fun setAlarmStatus(@Body body: HashMap<String, Any?>): Single<JsonObject>
}