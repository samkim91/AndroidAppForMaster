package kr.co.soogong.master.data.network.auth

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.auth.VersionDto
import kr.co.soogong.master.data.entity.auth.FirebaseTokenDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthInterface {

    @GET(HttpContract.IS_MASTER_EXISTENT)
    fun isMasterExistent(@Query("tel") tel: String): Single<Boolean>

    @POST(HttpContract.SAVE_FCM_TOKEN)
    fun saveFCMToken(@Body firebaseTokenDto: FirebaseTokenDto): Single<FirebaseTokenDto>

    @GET(HttpContract.GET_APP_VERSION)
    fun getVersion(): Single<VersionDto>
}