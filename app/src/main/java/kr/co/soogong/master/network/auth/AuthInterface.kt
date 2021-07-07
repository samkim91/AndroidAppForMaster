package kr.co.soogong.master.network.auth

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.auth.FirebaseTokenDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthInterface {

    @GET(HttpContract.GET_MASTER_BY_UID)
    fun signIn(@Query("uid") uid: String): Single<MasterDto>

    @GET(HttpContract.IS_MASTER_EXISTENT)
    fun isMasterExistent(@Query("tel") tel: String): Single<Boolean>

    @POST(HttpContract.SAVE_FCM_TOKEN)
    fun saveFCMToken(@Body firebaseTokenDto: FirebaseTokenDto): Single<FirebaseTokenDto>
}