package kr.co.soogong.master.domain.usecase.common.major

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.entity.common.verify
import kr.co.soogong.master.data.source.network.common.major.MajorService
import kr.co.soogong.master.domain.entity.common.major.Category
import javax.inject.Inject

@Reusable
class GetCategoriesUseCase @Inject constructor(
    private val majorService: MajorService,
) {
    suspend operator fun invoke(): List<Category> =
        withContext(Dispatchers.IO) {
            Category.fromCategoryDtos(majorService.getCategories().verify()!!)!!
        }
}