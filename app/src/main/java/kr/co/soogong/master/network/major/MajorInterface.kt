package kr.co.soogong.master.network.major

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.data.model.major.Project
import retrofit2.http.GET
import retrofit2.http.Query

interface MajorInterface {
    @GET(HttpContract.CATEGORY_LIST)
    fun getCategoryList(): Single<List<Category>>

    @GET(HttpContract.PROJECT_LIST)
    fun getProjectList(@Query("categoryId") categoryId: Int): Single<List<Project>>
}