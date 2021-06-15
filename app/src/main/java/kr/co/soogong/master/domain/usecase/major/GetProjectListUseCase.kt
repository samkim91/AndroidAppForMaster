package kr.co.soogong.master.domain.usecase.major

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.data.model.major.Project
import kr.co.soogong.master.network.major.CategoryService
import javax.inject.Inject

@Reusable
class GetProjectListUseCase @Inject constructor(
    private val categoryService: CategoryService
) {
    operator fun invoke(category: Category): Single<List<Project>> {
        return categoryService.getProjectList(category)
    }
}