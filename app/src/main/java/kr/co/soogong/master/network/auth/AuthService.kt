package kr.co.soogong.master.network.auth

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.auth.SignInDto
import kr.co.soogong.master.data.dto.Response
import retrofit2.Retrofit
import javax.inject.Inject

class AuthService @Inject constructor(
    retrofit: Retrofit
) {
    private val authInterface = retrofit.create(AuthInterface::class.java)


    fun signIn(signInDto: SignInDto): Single<JsonObject> {
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

        return authInterface.signIn(Gson().toJson(signInDto))
    }

    fun getId(id: String): Single<Response> {
        return authInterface.getId(id)
    }

    fun updateFCMToken(keycode: String?, fcmKey: String?): Single<Response> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode
        data["key"] = fcmKey

        return authInterface.updateFCMToken(data)
    }
}