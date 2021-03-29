package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.data.category.Project
import kr.co.soogong.master.network.CategoryService
import javax.inject.Inject

@Reusable
class GetProjectListUseCase @Inject constructor(
    private val categoryService: CategoryService
) {
    operator fun invoke(category: Category): Single<List<Project>> {
        return categoryService.getProjectList(category)
    }
}