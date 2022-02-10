package kr.co.soogong.master.presentation.ui.main

import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData

fun checkMasterApprovedStatus(
    fragmentManager: FragmentManager,
    mainViewModel: MainViewModel,
    onApproved: () -> Unit,
) {
    mainViewModel.masterSimpleInfo.value?.approvedStatus.let {
        when (it) {
            // 미승인 상태이면, 필수정보를 채우도록 이동
            CodeTable.NOT_APPROVED.code ->
                DefaultDialog.newInstance(
                    DialogData.getAskingFillProfile(),
                ).let { dialog ->
                    dialog.setButtonsClickListener(
                        onPositive = {
                            mainViewModel.selectedMainTabInMainActivity.value =
                                TAB_TEXTS_MAIN_NAVIGATION.indexOf(R.string.main_activity_navigation_bar_profile)
                        },
                        onNegative = { }
                    )
                    dialog.show(fragmentManager, dialog.tag)
                }
            // 승인요청 상태이면, 승인될 때까지 기다리라는 문구
            CodeTable.REQUEST_APPROVE.code ->
                DefaultDialog.newInstance(
                    DialogData.getWaitingUntilApproval()
                ).let { dialog ->
                    dialog.setButtonsClickListener(
                        onPositive = { },
                        onNegative = { }
                    )
                    dialog.show(fragmentManager, dialog.tag)
                }
            // 승인 상태이면, 함수 실행
            else -> onApproved()
        }
    }
}