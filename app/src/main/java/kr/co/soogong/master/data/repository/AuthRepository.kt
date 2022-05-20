package kr.co.soogong.master.data.repository

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.auth.FirebaseTokenDto
import kr.co.soogong.master.data.entity.auth.MasterSignUpDto
import kr.co.soogong.master.data.entity.profile.MasterSettingsDto
import kr.co.soogong.master.data.source.network.auth.AuthService
import kr.co.soogong.master.domain.entity.auth.MasterSignUp
import java.net.HttpURLConnection
import javax.inject.Inject

@Reusable
class AuthRepository @Inject constructor(
    private val authService: AuthService,
) {

    fun isMasterExistent(tel: String): Single<Boolean> =
        authService.isMasterExistent(tel)

    suspend fun requestCertificationCode(requestDto: Map<String, String>): Boolean {
        return authService.requestCertificationCode(requestDto)
    }

    suspend fun verifyCertificationCode(requestDto: Map<String, String>): Boolean {
        return authService.verifyCertificationCode(requestDto)
    }

    fun signUp(masterSignUpDto: MasterSignUpDto): Single<MasterSignUp> =
        authService.signUp(masterSignUpDto).map {
            MasterSignUp.fromDto(it.data!!)
        }

    fun signIn(uid: String, tel: String): Single<MasterSettingsDto> =
        authService.signIn(uid, tel)
            .map { responseDto ->
                if (responseDto.code.toInt() == HttpURLConnection.HTTP_OK)
                    responseDto.data
                else
                    throw Exception(responseDto.messageKo)
            }

    suspend fun saveFCMToken(firebaseTokenDto: FirebaseTokenDto) =
        authService.saveFCMToken(firebaseTokenDto)

    companion object {
        private const val TAG = "AuthRepository"
    }
}