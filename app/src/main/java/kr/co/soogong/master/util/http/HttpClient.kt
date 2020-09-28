package kr.co.soogong.master.util.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import kr.co.soogong.master.data.rawtype.sign.SignInfo
import kr.co.soogong.master.data.requirements.Estimate
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.ui.settings.notice.Notice
import kr.co.soogong.master.util.InjectHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object HttpClient {
    private val TAG = HttpClient::class.java.simpleName
    private const val URL = "https://api2.soogong.co.kr/"

    private lateinit var instance: HttpClient
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var httpInterface: HttpInterface

    init {
        updateHttpClient()
    }

    fun newInstance(): HttpClient {
        if (!::instance.isInitialized) {
            instance = HttpClient
        }
        return instance
    }

    private fun updateHttpClient() {
        okHttpClient = InjectHelper.getOkHttpClient()
        httpInterface = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(HttpInterface::class.java)
    }

    //region Auth
    fun login(email: String, password: String): Single<String> {
        val data = HashMap<String, String>()
        data["email"] = email
        data["password"] = password
        val send = HashMap<String, HashMap<String, String>>()
        send["customer"] = data

        return httpInterface.login(send).flatMap { responseBody ->
            val text = responseBody.string()

            if (text.contains("data")) {
                val signInfo: SignInfo =
                    Gson().fromJson(text, object : TypeToken<SignInfo>() {}.type)
                if (signInfo.data.attributes.keycode.isNullOrEmpty()) {
                    return@flatMap Single.error(RxException("기사 분들만 로그인이 가능 합니다"))
                } else {
                    return@flatMap Single.just(signInfo.data.attributes.keycode)
                }
            } else {
                val signInfo: Response =
                    Gson().fromJson(text, object : TypeToken<Response>() {}.type)

                return@flatMap Single.error(RxException(signInfo.message))
            }
        }
    }
    //endregion Auth

    fun getRequirementList(keycode: String?): Single<List<Requirement>> {
        return httpInterface.getRequirementList(keycode)
            .map { list -> list.map { Requirement.fromReceived(it) } }
    }

    fun getProgressList(keycode: String?): Single<List<Requirement>> {
        return httpInterface.getProgressList(keycode)
            .map { list -> list.map { Requirement.fromProgress(it) } }
    }

    fun refuseRequirement(branchKeycode: String?, keycode: String): Single<Response> {
        val data = HashMap<String, String?>()
        data["branch_keycode"] = branchKeycode
        data["keycode"] = keycode
        return httpInterface.refuseRequirement(data)
    }

    fun sendMessage(branchKeycode: String?, keycode: String, estimate: Estimate): Single<String> {
        val data = HashMap<String, String?>()
        data["branch_keycode"] = branchKeycode
        data["keycode"] = keycode
        data["price"] = estimate.price
        data["contents"] = estimate.contents
        data["possible_date"] = estimate.possibleDate
        return httpInterface.sendMessage(data)
    }

    fun updateFCMToken(keycode: String?, fcmKey: String): Single<Response> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode
        data["key"] = fcmKey

        return httpInterface.updateFCMToken(data)
    }

    fun getUserProfile(keycode: String?): Single<User> {
        return httpInterface.getUserProfile(keycode).map {
            Timber.tag(TAG).d("getUserProfile: $it")
            User.from(it)
        }
    }

    fun getNoticeList(): Single<List<Notice>> {
        return httpInterface.getNoticeList()
    }
}
