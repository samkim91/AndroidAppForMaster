package kr.co.soogong.master.util.http

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.data.category.Project
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.data.requirements.Estimate
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.data.user.SignInInfo
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.util.InjectHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

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

        return httpInterface.login(send).flatMap { json ->

            try {
                val signInInfo = SignInInfo.from(json)

                if (signInInfo.keycode.isEmpty()) {
                    return@flatMap Single.error(RxException("기사 분들만 로그인이 가능 합니다"))
                } else {
                    return@flatMap Single.just(signInInfo.keycode)
                }
            } catch (e: Exception) {
                val item = json.get("message").asString

                return@flatMap Single.error(RxException(item))
            }
        }
    }

    fun actionSignUp(signUpInfo: SignUpInfo): Single<JsonObject> {
        val data = HashMap<String, String>()
        data["email"] = signUpInfo.email
        data["password"] = signUpInfo.password
        data["password_confirmation"] = signUpInfo.passwordConfirmation
        data["username"] = signUpInfo.username
        data["phone_number"] = signUpInfo.phoneNumber
        data["customer_type"] = signUpInfo.customerType
        val send = HashMap<String, HashMap<String, String>>()
        send["customer"] = data
        return httpInterface.signup(send).flatMap { jsonObject ->
            if (jsonObject.has("data")) {
                Timber.tag(TAG).d("actionSignUp: $jsonObject")

                val token = jsonObject.get("data").asJsonObject
                    .get("attributes").asJsonObject
                    .get("token").asString

                val masterData = HashMap<String, String>()
                masterData["area"] = signUpInfo.area
                masterData["location"] = signUpInfo.location
                masterData["business_number"] = signUpInfo.businessNumber
                masterData["name"] = signUpInfo.username
                masterData["tel"] = signUpInfo.tel
                masterData["address"] = signUpInfo.address
                masterData["detail_address"] = signUpInfo.detailAddress
                masterData["description"] = signUpInfo.description
                masterData["open_date"] = signUpInfo.openDate
                masterData["status"] = "requested"

                return@flatMap httpInterface.registerMaster(token, masterData)
            } else {
                Timber.tag(TAG).w("actionSignUp: $jsonObject")
                return@flatMap Single.error(RxException(message = "fail", data = jsonObject))
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

    fun getCategoryList(): Single<List<Category>> {
        return httpInterface.getCategoryList().map {
            val items: MutableList<Category> = ArrayList()
            for (item in it) {
                items.add(Category.fromJson(item))
            }
            return@map items
        }
    }

    fun getProjectList(category: Category): Single<List<Project>> {
        return httpInterface.getProjectList(category.id).map {
            val items: MutableList<Project> = ArrayList()
            for (item in it) {
                items.add(Project.fromJson(item))
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

    fun updateFCMToken(keycode: String?, fcmKey: String?): Single<Response> {
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
            if (req.get("status").asInt == 200) {
                Timber.tag(TAG).d("getAlarmStatus: ${req.get("status").asInt}")
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
