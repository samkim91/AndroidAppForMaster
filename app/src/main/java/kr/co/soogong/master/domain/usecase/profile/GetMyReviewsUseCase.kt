package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.data.profile.MyReview
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.ProfileService
import javax.inject.Inject

@Reusable
class GetMyReviewsUseCase @Inject constructor(
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
    private val profileService: ProfileService,
) {
    suspend operator fun invoke(): MyReview {
        return profileService.getMyReviews(getMasterKeyCodeUseCase()!!)
    }
}