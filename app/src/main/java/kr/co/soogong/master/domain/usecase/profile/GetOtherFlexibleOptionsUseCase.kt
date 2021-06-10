package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.OtherFlexibleOptions
import javax.inject.Inject

@Reusable
class GetOtherFlexibleOptionsUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<OtherFlexibleOptions> {
        return getProfileFromLocalUseCase().map { profile ->
            profile.basicInformation?.otherFlexibleOptions
        }
    }
}