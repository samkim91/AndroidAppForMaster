package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.MyReview
import javax.inject.Inject

@Reusable
class GetMyReviewsUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): MyReview {
        if(BuildConfig.DEBUG) {
            return MyReview.TEST_MY_REVIEW
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.basicInformation?.myReviews ?: MyReview.NULL_MY_REVIEW
        }
    }
}