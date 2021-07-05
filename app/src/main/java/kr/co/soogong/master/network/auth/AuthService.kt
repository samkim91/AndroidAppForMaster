package kr.co.soogong.master.network.auth

import io.reactivex.Single
import kr.co.soogong.master.data.dto.auth.FirebaseTokenDto
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

    // TODO: 2021/06/23 master save 와 같은 API이기 때문에, 통합해도 됨..
    fun signUp(masterDto: MasterDto): Single<MasterDto> {
        return authInterface.signUp(masterDto)
    }

    fun checkUserExistent(id: String): Single<Boolean> {
        return authInterface.checkUserExistent(id)
    }

    fun isMasterExistent(tel: String): Single<Boolean> {
        return authInterface.isMasterExistent(tel)
    }

    fun saveFCMToken(firebaseTokenDto: FirebaseTokenDto): Single<FirebaseTokenDto> {
        return authInterface.saveFCMToken(firebaseTokenDto)
    }
}