package kr.co.soogong.master.domain.usecase.major

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.network.major.CategoryService
import javax.inject.Inject

@Reusable
class GetCategoryListUseCase @Inject constructor(
    private val categoryService: CategoryService
){
    operator fun invoke(): Single<List<Category>> {
        return categoryService.getCategoryList()
    }
}