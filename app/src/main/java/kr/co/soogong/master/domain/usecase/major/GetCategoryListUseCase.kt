package kr.co.soogong.master.domain.usecase.major

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.CategoryDto
import kr.co.soogong.master.network.major.MajorService
import javax.inject.Inject

@Reusable
class GetCategoryListUseCase @Inject constructor(
    private val majorService: MajorService,
) {
    operator fun invoke(): Single<List<CategoryDto>> {
        return majorService.getCategoryList()
    }
}