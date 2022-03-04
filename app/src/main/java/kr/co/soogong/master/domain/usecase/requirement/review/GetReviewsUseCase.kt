package kr.co.soogong.master.domain.usecase.requirement.review

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.repository.ReviewRepository
import kr.co.soogong.master.domain.entity.profile.Review
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import javax.inject.Inject

@Reusable
class GetReviewsUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(
        offset: Int,
        pageSize: Int,
    ): Single<PageableContentDto<Review>> {
        return reviewRepository.getReviews(getMasterUidFromSharedUseCase(),
            offset,
            pageSize,
            1,
            "id").map { pageableContentDto ->
            PageableContentDto(
                content = pageableContentDto.content.map { reviewDto ->
                    Review.fromReviewDto(reviewDto)
                },
                pageable = pageableContentDto.pageable,
                last = pageableContentDto.last,
                numberOfElements = pageableContentDto.numberOfElements
            )
        }
    }
}
