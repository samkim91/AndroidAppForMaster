package kr.co.soogong.master.util.http

import io.reactivex.Single
import kr.co.soogong.master.data.rawtype.requirment.RawRequirementItem
import kr.co.soogong.master.data.rawtype.user.RawUser
import kr.co.soogong.master.ui.settings.notice.Notice
import okhttp3.ResponseBody
import retrofit2.http.*

interface HttpInterface {
    @POST("login")
    fun login(@Body body: HashMap<String, HashMap<String, String>>): Single<ResponseBody>

    @GET("api/v1/transmissions/default_list")
    fun getRequirementList(@Query("branch_keycode") auth: String?): Single<List<RawRequirementItem>>

    @GET("api/v1/transmissions/accept_list")
    fun getProgressList(@Query("branch_keycode") auth: String?): Single<List<RawRequirementItem>>

    @POST("api/v1/transmissions/refuse")
    fun refuseRequirement(@Body body: HashMap<String, String?>): Single<Response>

    @POST("api/v1/transmissions/send_message")
    fun sendMessage(@Body body: HashMap<String, String?>): Single<String>

    @POST("api/v1/branches/update_reg_id")
    fun updateFCMToken(@Body body: HashMap<String, String?>): Single<Response>

    @GET("api/v1/branches/search/{keycode}")
    fun getUserProfile(@Path("keycode") keycode: String?): Single<RawUser>

    @GET()
    fun getNoticeList(): Single<List<Notice>>
}