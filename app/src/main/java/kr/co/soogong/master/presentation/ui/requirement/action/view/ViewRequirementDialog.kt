package kr.co.soogong.master.presentation.ui.requirement.action.view

import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData

fun showDialogForCallingCustomer(
    viewRequirementActivity: ViewRequirementActivity,
    viewModel: ViewRequirementViewModel,
    requirement: Requirement,
) {
    when (requirement.status) {
        // NOTE: 방문예정 상태에서, 상호 통화한 적이 한번도 없으면 전화하라고 안내
        is RequirementStatus.Measuring -> {
            if (requirement.estimation.fromMasterCallCnt == 0 && requirement.estimation.fromClientCallCnt == 0) {
                DefaultDialog.newInstance(DialogData.getSendMeasuringDate())
                    .let {
                        it.setButtonsClickListener(
                            onPositive = {
                                // TODO: 2022/06/09 방문일 입력을 viewModel 로 변경하고 적용
                            },
                            onNegative = {}
                        )
                        it.show(viewRequirementActivity.supportFragmentManager, it.tag)
                    }
            }
        }
        else -> Unit
    }
}