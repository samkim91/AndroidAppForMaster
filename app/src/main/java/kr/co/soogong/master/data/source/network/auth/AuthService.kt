package kr.co.soogong.master.data.source.network.auth

import io.reactivex.Single
import kr.co.soogong.master.data.entity.auth.FirebaseTokenDto
import kr.co.soogong.master.data.entity.auth.MasterSignUpDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import retrofit2.Retrofit
import javax.inject.Inject

class AuthService @Inject constructor(
    retrofit: Retrofit
) {
    private val authInterface = retrofit.create(AuthInterface::class.java)

    fun isMasterExistent(tel: String): Single<Boolean> {
        return authInterface.isMasterExistent(tel)
    }

    fun signUp(masterSignUpDto: MasterSignUpDto): Single<ResponseDto<MasterSignUpDto>> = authInterface.signUp(masterSignUpDto)

    suspend fun saveFCMToken(firebaseTokenDto: FirebaseTokenDto) {
        return authInterface.saveFCMToken(firebaseTokenDto)
    }
}