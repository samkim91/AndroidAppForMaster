package kr.co.soogong.master.data.repository

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.source.network.auth.AuthService
import kr.co.soogong.master.data.entity.auth.FirebaseTokenDto
import kr.co.soogong.master.data.entity.auth.MasterSignUpDto
import kr.co.soogong.master.domain.entity.auth.MasterSignUp
import javax.inject.Inject

@Reusable
class AuthRepository @Inject constructor(
    private val authService: AuthService,
) {

    fun isMasterExistent(tel: String): Single<Boolean> =
        authService.isMasterExistent(tel)

    fun signUp(masterSignUpDto: MasterSignUpDto): Single<MasterSignUp> =
        authService.signUp(masterSignUpDto).map {
            MasterSignUp.fromDto(it.data!!)
        }


    fun saveFCMToken(firebaseTokenDto: FirebaseTokenDto): Single<FirebaseTokenDto> =
        authService.saveFCMToken(firebaseTokenDto)

    companion object {
        private const val TAG = "AuthRepository"
    }
}