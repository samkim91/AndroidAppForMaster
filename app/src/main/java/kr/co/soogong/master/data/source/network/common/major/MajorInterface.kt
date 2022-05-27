package kr.co.soogong.master.data.source.network.common.major

import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.common.major.CategoryDto
import kr.co.soogong.master.data.entity.common.major.ProjectDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MajorInterface {
    @GET(HttpContract.CATEGORIES)
    suspend fun getCategories(): ResponseDto<List<CategoryDto>>

    @GET(HttpContract.PROJECTS)
    suspend fun getProjects(@Query("categoryId") categoryId: Int): List<ProjectDto>
}