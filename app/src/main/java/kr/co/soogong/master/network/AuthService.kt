package kr.co.soogong.master.network

import com.google.gson.Gson
import io.reactivex.Single
import kr.co.soogong.master.data.user.SignInInfo
import kr.co.soogong.master.data.user.SignUpInfo
import retrofit2.Retrofit
import javax.inject.Inject

class AuthService @Inject constructor(
    retrofit: Retrofit
) {
    private val authInterface = retrofit.create(AuthInterface::class.java)

    fun login(phoneNumber: String?, password: String?): Single<SignInInfo> {
        val data = HashMap<String, String?>()
        data["phone_number"] = phoneNumber
        data["password"] = password
        val send = HashMap<String, HashMap<String, String?>>()
        send["customer"] = data

        return authInterface.login(send).flatMap { json ->

            try {
                val signInInfo = SignInInfo.fromJson(json)

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

    fun findPassword(name: String?): Single<Response> {
        val data = HashMap<String, String?>()
        data["name"] = name
        return authInterface.findPassword(data)
    }

    fun signUp(signUpInfo: SignUpInfo): Single<Response> {
        val data = HashMap<String, Any>()
        data["phone_number"] = signUpInfo.phoneNumber
        data["password"] = signUpInfo.password
        data["business_representative_name"] = signUpInfo.businessRepresentativeName
        data["business_type"] = signUpInfo.businessType
        data["address"] = signUpInfo.address
        data["sub_address"] = signUpInfo.subAddress
        data["latitude"] = signUpInfo.latitude
        data["longitude"] = signUpInfo.longitude
        data["service_area"] = signUpInfo.serviceArea

        data["accept_privacy_policy"] = signUpInfo.acceptPrivacyPolicy
        data["app_push"] = signUpInfo.appPush
        data["app_push_at_night"] = signUpInfo.appPushAtNight
        data["kakao_alarm"] = signUpInfo.kakaoAlarm
        data["sms_alarm"] = signUpInfo.smsAlarm

        val send = HashMap<String, HashMap<String, Any>>()
        send["master_sign_up_info"] = data

        return authInterface.signUp(send)
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