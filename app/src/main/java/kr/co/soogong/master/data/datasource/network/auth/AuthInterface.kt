package kr.co.soogong.master.data.datasource.network.auth

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.auth.FirebaseTokenDto
import kr.co.soogong.master.data.entity.auth.MasterSignUpDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import retrofit2.http.*

interface AuthInterface {

    @GET(HttpContract.IS_MASTER_EXISTENT)
    fun isMasterExistent(@Query("tel") tel: String): Single<Boolean>

    @POST(HttpContract.SIGN_UP)
    fun signUp(@Body masterSignUpDto: MasterSignUpDto): Single<ResponseDto<MasterSignUpDto>>

    @POST(HttpContract.SAVE_FCM_TOKEN)
    fun saveFCMToken(@Body firebaseTokenDto: FirebaseTokenDto): Single<FirebaseTokenDto>
}