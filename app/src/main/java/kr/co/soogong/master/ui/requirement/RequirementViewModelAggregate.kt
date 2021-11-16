package kr.co.soogong.master.ui.requirement

import kr.co.soogong.master.domain.usecase.common.GetNoticeForCallingFromSharedUseCase
import kr.co.soogong.master.domain.usecase.common.SaveNoticeForCallingInSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateDirectRepairYnUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateRequestMeasureYnUseCase
import kr.co.soogong.master.domain.usecase.requirement.*
import javax.inject.Inject

class RequirementViewModelAggregate @Inject constructor(
    val getRequirementCardsUseCase: GetRequirementCardsUseCase,
    val getMasterSimpleInfoUseCase: GetMasterSimpleInfoUseCase,
    val callToClientUseCase: CallToClientUseCase,
    val requestReviewUseCase: RequestReviewUseCase,
    val updateRequestMeasureYnUseCase: UpdateRequestMeasureYnUseCase,
    val updateDirectRepairYnUseCase: UpdateDirectRepairYnUseCase,
    val searchRequirementCardsUseCase: SearchRequirementCardsUseCase,
    val getCustomerRequestsUseCase: GetCustomerRequestsUseCase,
    val saveNoticeForCallingInSharedUseCase: SaveNoticeForCallingInSharedUseCase,
    val getNoticeForCallingFromSharedUseCase: GetNoticeForCallingFromSharedUseCase,
) {}