package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.MyReview
import javax.inject.Inject

@Reusable
class GetMyReviewsUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<MyReview> {
//        if(BuildConfig.DEBUG) {
//            return MyReview.TEST_MY_REVIEW
//        }

        // TODO: 2021/06/15 수정작업
        return Single.just(MyReview.NULL_MY_REVIEW)

//        return getProfileFromLocalUseCase().map { profile ->
//            profile.basicInformation?.myReviews
//        }
    }
}