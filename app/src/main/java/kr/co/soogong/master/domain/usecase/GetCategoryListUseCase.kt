package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.network.CategoryService
import javax.inject.Inject

@Reusable
class GetCategoryListUseCase @Inject constructor(
    private val categoryService: CategoryService
){
    operator fun invoke(): Single<List<Category>> {
        return categoryService.getCategoryList()
    }
}