package kr.co.soogong.master.network.major

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import org.json.JSONArray
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface MajorInterface {
//    @GET(HttpContract.CATEGORY_LIST)
//    fun getCategoryList(): Single<List<JsonObject>>
//
//    @GET(HttpContract.PROJECT_LIST)
//    fun getProjectList(@Path("id") id: Int): Single<List<JsonObject>>

    @GET
    fun getCategoryList(@Url url: String): Single<JsonArray>

    @GET
    fun getProjectList(@Url url: String): Single<JsonArray>
}