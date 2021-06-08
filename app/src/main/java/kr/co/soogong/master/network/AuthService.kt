package kr.co.soogong.master.network

import com.google.gson.Gson
import io.reactivex.Single
import kr.co.soogong.master.data.auth.ResponseSignInDto
import kr.co.soogong.master.data.auth.SignUpDto
import retrofit2.Retrofit
import javax.inject.Inject

class AuthService @Inject constructor(
    retrofit: Retrofit
) {
    private val authInterface = retrofit.create(AuthInterface::class.java)

    fun login(phoneNumber: String?, password: String?): Single<ResponseSignInDto> {
        val data = HashMap<String, String?>()
        data["phone_number"] = phoneNumber
        data["password"] = password
        val send = HashMap<String, HashMap<String, String?>>()
        send["customer"] = data

        return authInterface.login(send).flatMap { json ->

            try {
                val signInInfo = ResponseSignInDto.fromJson(json)

                if (signInInfo.keycode.isNullOrEmpty()) {
                    return@flatMap Single.error(RxException("기사 분들만 로그인이 가능 합니다"))
                } else {
                    return@flatMap Single.just(signInInfo)
                }
            } catch (e: Exception) {
                val item = json.get("message").asString

                return@flatMap Single.error(RxException(item))
            }
        }
    }

    fun resignIn(refreshToken: String): Single<Response> {
        val params = HashMap<String, String?>()
        params["refreshToken"] = refreshToken

        return authInterface.resignIn(params)
    }

    fun findPassword(name: String?): Single<Response> {
        val data = HashMap<String, String?>()
        data["name"] = name
        return authInterface.findPassword(data)
    }

    fun signUp(signUpDto: SignUpDto): Single<Response> {
//        val data = HashMap<String, Any>()
//        val values = Gson().toJson(signUpInfo)

//        data["phoneNumber"] = signUpInfo.phoneNumber
//        data["password"] = signUpInfo.password
//        data["ownerName"] = signUpInfo.businessRepresentativeName
//        data["businessType"] = Gson().toJson(signUpInfo.businessType)
//        data["address"] = signUpInfo.address
//        data["subAddress"] = signUpInfo.subAddress
//        data["latitude"] = signUpInfo.latitude
//        data["longitude"] = signUpInfo.longitude
//        data["serviceArea"] = signUpInfo.serviceArea
//        data["privacyPolicy"] = signUpInfo.privacyPolicy
//        data["appPush"] = signUpInfo.appPush
//        data["marketingPush"] = signUpInfo.marketingPush

        return authInterface.signUp(Gson().toJson(signUpDto))
    }

    fun passwordChange(
        keycode: String?,
        password: String?,
        confirmPassword: String?
    ): Single<Response> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode
        data["password"] = password
        data["password_confirmation"] = confirmPassword

        return authInterface.passwordChange(data)
    }

    fun updateFCMToken(keycode: String?, fcmKey: String?): Single<Response> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode
        data["key"] = fcmKey

        return authInterface.updateFCMToken(data)
    }

    fun checkIdExist(id: String?): Single<Response>{
        val data = HashMap<String, String?>()
        data["phone_number"] = id

        return authInterface.checkIdExist(data)
    }
}