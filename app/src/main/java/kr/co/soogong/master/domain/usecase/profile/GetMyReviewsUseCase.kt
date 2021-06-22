package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.MyReview
import javax.inject.Inject

@Reusable
class GetMyReviewsUseCase @Inject constructor(
    private val getMasterFromLocalUseCase: GetMasterFromLocalUseCase,
) {
    operator fun invoke(): Single<MyReview> {
        return getMasterFromLocalUseCase().map {
            it.myReview
        }
    }
}