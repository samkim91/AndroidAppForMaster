package kr.co.soogong.master.util.http

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.data.rawtype.sign.SignInfo
import kr.co.soogong.master.data.requirements.Estimate
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.util.InjectHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import kotlin.collections.set

object HttpClient {
    private val TAG = HttpClient::class.java.simpleName
    private val URL = if (BuildConfig.DEBUG) {
        "https://test.api2.soogong.co.kr/"
    } else {
        "https://api2.soogong.co.kr/"
    }


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

    fun findInfo(name: String?, contact: String?): Single<Response> {
        val data = HashMap<String, String?>()
        data["name"] = name
        data["contact"] = contact
        return httpInterface.findInfo(data)
    }
    //endregion Auth

    fun getCategories(): Single<List<String>> {
        return httpInterface.getCategories().map {
            val items: MutableList<String> = ArrayList()
            for (item in it) {
                val temp = item.get("attributes").asJsonObject.get("name").asString
                items.add(temp)
            }
            return@map items
        }
    }

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

    //region Setting
    fun getNoticeList(`for`: String = "free"): Single<List<Notice>> {
        return httpInterface.getNoticeList(`for`).map { list ->
            val ret = ArrayList<Notice>()
            for (item in list) {
                ret.add(Notice.from(item))
            }
            return@map ret
        }
    }

    fun resetPassword(
        keycode: String?,
        password: String,
        confirmPassword: String
    ): Single<Response> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode
        data["password"] = password
        data["password_confirmation"] = confirmPassword

        return httpInterface.resetPassword(data)
    }

    fun getAlarmStatus(keycode: String?): Single<HashMap<String, Boolean>> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode

        return httpInterface.getAlarmStatus(data).map { req ->
            val map = HashMap<String, Boolean>()
            if (req.get("status").asNumber == 200) {
                val array = req.get("data").asJsonArray
                for (item in array) {
                    map[item.asJsonObject.get("variable_type").asString] =
                        item.asJsonObject.get("value").asBoolean
                }
            }
            return@map map
        }
    }


    fun setAlarmStatus(keycode: String?, type: String, value: Boolean): Single<JsonObject> {
        val data = HashMap<String, Any?>()
        data["keycode"] = keycode
        data["type"] = type
        data["value"] = value

        return httpInterface.setAlarmStatus(data).map {
            return@map it
        }
    }
    //endregion
}
