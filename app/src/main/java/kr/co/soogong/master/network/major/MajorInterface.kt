package kr.co.soogong.master.network.major

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.profile.CategoryDto
import kr.co.soogong.master.data.dto.profile.ProjectDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MajorInterface {
    @GET(HttpContract.CATEGORY_LIST)
    fun getCategoryList(): Single<List<CategoryDto>>

    @GET(HttpContract.PROJECT_LIST)
    fun getProjectList(@Query("categoryId") categoryId: Int): Single<List<ProjectDto>>
}