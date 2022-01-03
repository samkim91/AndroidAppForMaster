package kr.co.soogong.master.network.major

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.major.CategoryDto
import kr.co.soogong.master.data.dto.major.ProjectDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MajorInterface {
    @GET(HttpContract.CATEGORIES)
    fun getCategories(): Single<List<CategoryDto>>

    @GET(HttpContract.PROJECTS)
    fun getProjects(@Query("categoryId") categoryId: Int): Single<List<ProjectDto>>
}