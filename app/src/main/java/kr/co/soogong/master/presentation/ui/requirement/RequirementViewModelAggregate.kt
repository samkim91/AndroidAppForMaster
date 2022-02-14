package kr.co.soogong.master.presentation.ui.requirement

import kr.co.soogong.master.domain.usecase.profile.UpdateRequestMeasureYnUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementCardsUseCase
import kr.co.soogong.master.domain.usecase.requirement.RequestReviewUseCase
import kr.co.soogong.master.domain.usecase.requirement.RespondToMeasureUseCase
import javax.inject.Inject

class RequirementViewModelAggregate @Inject constructor(
    val getRequirementCardsUseCase: GetRequirementCardsUseCase,
    val callToClientUseCase: CallToClientUseCase,
    val requestReviewUseCase: RequestReviewUseCase,
    val respondToMeasureUseCase: RespondToMeasureUseCase,
    val updateRequestMeasureYnUseCase: UpdateRequestMeasureYnUseCase,
)