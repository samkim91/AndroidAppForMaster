package kr.co.soogong.master.domain.usecase.major

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.network.major.MajorService
import javax.inject.Inject

@Reusable
class GetCategoriesUseCase @Inject constructor(
    private val majorService: MajorService,
) {
    operator fun invoke(): Single<List<Category>> = majorService.getCategories().map { categories ->
        Category.fromCategoryDtos(categories)
    }
}