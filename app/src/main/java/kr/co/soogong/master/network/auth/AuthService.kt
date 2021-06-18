package kr.co.soogong.master.network.auth

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.profile.MasterDto
import retrofit2.Retrofit
import javax.inject.Inject

class AuthService @Inject constructor(
    retrofit: Retrofit
) {
    private val authInterface = retrofit.create(AuthInterface::class.java)

    fun signIn(uid: String): Single<MasterDto> {
        return authInterface.signIn(uid)
    }

    fun signUp(masterDtoString: String): Single<MasterDto> {
        return authInterface.signUp(masterDtoString)
    }

    fun checkUserExistent(id: String): Single<Boolean> {
        return authInterface.checkUserExistent(id)
    }

    fun updateFCMToken(keycode: String?, fcmKey: String?): Single<Response> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode
        data["key"] = fcmKey

        return authInterface.updateFCMToken(data)
    }
}