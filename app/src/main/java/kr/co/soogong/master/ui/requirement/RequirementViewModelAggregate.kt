package kr.co.soogong.master.ui.requirement

import kr.co.soogong.master.domain.usecase.profile.UpdateRequestMeasureYnUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetCustomerRequestsUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementCardsUseCase
import kr.co.soogong.master.domain.usecase.requirement.RequestReviewUseCase
import javax.inject.Inject

class RequirementViewModelAggregate @Inject constructor(
    val getRequirementCardsUseCase: GetRequirementCardsUseCase,
    val callToClientUseCase: CallToClientUseCase,
    val requestReviewUseCase: RequestReviewUseCase,
    val updateRequestMeasureYnUseCase: UpdateRequestMeasureYnUseCase,
)