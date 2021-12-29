package kr.co.soogong.master.network.major

import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.CategoryDto
import kr.co.soogong.master.data.dto.profile.ProjectDto
import retrofit2.Retrofit
import javax.inject.Inject

class MajorService @Inject constructor(
    retrofit: Retrofit,
) {
    private val categoryInterface = retrofit.create(MajorInterface::class.java)

    fun getCategoryList(): Single<List<CategoryDto>> {
        return categoryInterface.getCategoryList()
    }

    fun getProjectList(categoryId: Int): Single<List<ProjectDto>> {
        return categoryInterface.getProjectList(categoryId)
    }
}