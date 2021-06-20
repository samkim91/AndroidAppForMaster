package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.requirement.RequirementCard
import javax.inject.Inject

@Reusable
class GetReceivedRequirementListUseCase @Inject constructor(
    private val getRequirementListUseCase: GetRequirementListUseCase,
) {
    operator fun invoke(): Single<List<RequirementCard>> {
        return getRequirementListUseCase(listOf("Requested", "Estimated"))
    }
}