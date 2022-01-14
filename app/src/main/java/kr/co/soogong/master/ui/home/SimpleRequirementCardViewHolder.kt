package kr.co.soogong.master.ui.home

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.global.CodeTable
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderSimpleRequirementItemBinding
import kr.co.soogong.master.ui.dialog.popup.DefaultDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.main.MainViewModel
import kr.co.soogong.master.ui.main.TAB_TEXTS_MAIN_NAVIGATION
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper

class SimpleRequirementCardViewHolder(
    private val binding: ViewHolderSimpleRequirementItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        mainViewModel: MainViewModel,
        requirementCard: RequirementCard,
        position: Int,
    ) {
        with(binding) {
            data = requirementCard
            number = if (position < 9) "0${position + 1}" else "${position + 1}"

            setApprovedMasterOnly(context,
                fragmentManager,
                this,
                mainViewModel,
                requirementCard)
        }
    }

    private fun setApprovedMasterOnly(
        context: Context,
        fragmentManager: FragmentManager,
        binding: ViewHolderSimpleRequirementItemBinding,
        mainViewModel: MainViewModel,
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            setCardClickListener {
                mainViewModel.masterSimpleInfo.value?.approvedStatus.let {
                    when (it) {
                        // 미승인 상태이면, 필수정보를 채우도록 이동
                        CodeTable.NOT_APPROVED.code -> {
                            DefaultDialog.newInstance(
                                DialogData.getAskingFillProfileDialogData(),
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
                        }
                        // 승인요청 상태이면, 승인될 때까지 기다리라는 문구
                        CodeTable.REQUEST_APPROVE.code -> {
                            DefaultDialog.newInstance(
                                DialogData.getWaitingUntilApprovalDialogData()
                            ).let { dialog ->
                                dialog.setButtonsClickListener(
                                    onPositive = { },
                                    onNegative = { }
                                )
                                dialog.show(fragmentManager, dialog.tag)
                            }
                        }
                        // 승인 상태이면, 문의 세부정보로 이동
                        else -> {
                            context.startActivity(
                                ViewRequirementActivityHelper.getIntent(
                                    context,
                                    requirementCard.id,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

