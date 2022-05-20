package kr.co.soogong.master.data.source.network.auth

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.auth.FirebaseTokenDto
import kr.co.soogong.master.data.entity.auth.MasterSignUpDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.profile.MasterSettingsDto
import retrofit2.http.*

interface AuthInterface {

    @GET(HttpContract.IS_MASTER_EXISTENT)
    fun isMasterExistent(@Query("tel") tel: String): Single<Boolean>

    @POST(HttpContract.REQUEST_CERTIFICATION_CODE)
    suspend fun requestCertificationCode(@Body requestDto: Map<String, String>): Boolean

    @POST(HttpContract.VERIFY_CERTIFICATION_CODE)
    suspend fun verifyCertificationCode(@Body verifyDto: Map<String, String>): Boolean

    @POST(HttpContract.SIGN_UP)
    fun signUp(@Body masterSignUpDto: MasterSignUpDto): Single<ResponseDto<MasterSignUpDto>>

    @GET(HttpContract.SIGN_IN)
    fun signIn(@Query("uid") uid: String, @Query("tel") tel: String): Single<ResponseDto<MasterSettingsDto>>

    @POST(HttpContract.SAVE_FCM_TOKEN)
    suspend fun saveFCMToken(@Body firebaseTokenDto: FirebaseTokenDto)
}