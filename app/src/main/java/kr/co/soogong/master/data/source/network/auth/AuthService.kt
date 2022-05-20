package kr.co.soogong.master.data.source.network.auth

import io.reactivex.Single
import kr.co.soogong.master.data.entity.auth.FirebaseTokenDto
import kr.co.soogong.master.data.entity.auth.MasterSignUpDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.profile.MasterSettingsDto
import retrofit2.Retrofit
import javax.inject.Inject

class AuthService @Inject constructor(
    retrofit: Retrofit,
) {
    private val authInterface = retrofit.create(AuthInterface::class.java)

    fun isMasterExistent(tel: String): Single<Boolean> {
        return authInterface.isMasterExistent(tel)
    }

    suspend fun requestCertificationCode(requestDto: Map<String, String>): Boolean {
        return authInterface.requestCertificationCode(requestDto)
    }

    suspend fun verifyCertificationCode(verifyDto: Map<String, String>): Boolean {
        return authInterface.verifyCertificationCode(verifyDto)
    }

    fun signUp(masterSignUpDto: MasterSignUpDto): Single<ResponseDto<MasterSignUpDto>> =
        authInterface.signUp(masterSignUpDto)

    fun signIn(uid: String, tel: String): Single<ResponseDto<MasterSettingsDto>> {
        return authInterface.signIn(uid, tel)
    }

    suspend fun saveFCMToken(firebaseTokenDto: FirebaseTokenDto) {
        return authInterface.saveFCMToken(firebaseTokenDto)
    }
}