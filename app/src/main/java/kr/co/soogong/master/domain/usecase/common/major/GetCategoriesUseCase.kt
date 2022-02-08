package kr.co.soogong.master.domain.usecase.common.major

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.entity.common.major.Category
import kr.co.soogong.master.data.network.common.major.MajorService
import javax.inject.Inject

@Reusable
class GetCategoriesUseCase @Inject constructor(
    private val majorService: MajorService,
) {
    operator fun invoke(): Single<List<Category>> = majorService.getCategories().map { categories ->
        Category.fromCategoryDtos(categories)
    }
}