package kr.co.soogong.master.presentation.ui.requirement

import kr.co.soogong.master.domain.usecase.requirement.estimation.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementCardsUseCase
import kr.co.soogong.master.domain.usecase.requirement.review.RequestReviewUseCase
import kr.co.soogong.master.domain.usecase.requirement.estimation.AcceptToMeasureUseCase
import javax.inject.Inject

class RequirementViewModelAggregate @Inject constructor(
    val getRequirementCardsUseCase: GetRequirementCardsUseCase,
    val callToClientUseCase: CallToClientUseCase,
    val requestReviewUseCase: RequestReviewUseCase,
    val acceptToMeasureUseCase: AcceptToMeasureUseCase,
)