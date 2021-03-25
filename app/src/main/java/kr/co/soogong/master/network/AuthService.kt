package kr.co.soogong.master.network

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.data.user.SignInInfo
import kr.co.soogong.master.data.user.SignUpInfo
import retrofit2.Retrofit
import javax.inject.Inject

class AuthService @Inject constructor(
    retrofit: Retrofit
) {
    private val authInterface = retrofit.create(AuthInterface::class.java)

    fun login(email: String, password: String): Single<String> {
        val data = HashMap<String, String>()
        data["email"] = email
        data["password"] = password
        val send = HashMap<String, HashMap<String, String>>()
        send["customer"] = data

        return authInterface.login(send).flatMap { json ->

            try {
                val signInInfo = SignInInfo.fromJson(json)

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

    fun findInfo(name: String?): Single<Response> {
        val data = HashMap<String, String?>()
        data["name"] = name
        return authInterface.findInfo(data)
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
        return authInterface.signup(send).flatMap { jsonObject ->
            if (jsonObject.has("data")) {
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

                return@flatMap authInterface.registerMaster(token, masterData)
            } else {
                return@flatMap Single.error(RxException(message = "fail", data = jsonObject))
            }
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

        return authInterface.resetPassword(data)
    }

    fun updateFCMToken(keycode: String?, fcmKey: String?): Single<Response> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode
        data["key"] = fcmKey

        return authInterface.updateFCMToken(data)
    }
}