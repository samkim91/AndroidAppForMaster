package kr.co.soogong.master.network.auth

import io.reactivex.Single
import kr.co.soogong.master.data.dto.auth.VersionDto
import kr.co.soogong.master.data.dto.auth.FirebaseTokenDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import retrofit2.Retrofit
import javax.inject.Inject

class AuthService @Inject constructor(
    retrofit: Retrofit
) {
    private val authInterface = retrofit.create(AuthInterface::class.java)

    fun isMasterExistent(tel: String): Single<Boolean> {
        return authInterface.isMasterExistent(tel)
    }

    fun saveFCMToken(firebaseTokenDto: FirebaseTokenDto): Single<FirebaseTokenDto> {
        return authInterface.saveFCMToken(firebaseTokenDto)
    }

    fun getVersion(): Single<VersionDto> {
        return authInterface.getVersion()
    }
}