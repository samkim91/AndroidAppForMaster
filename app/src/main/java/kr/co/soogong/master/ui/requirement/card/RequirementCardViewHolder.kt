package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.ui.dialog.popup.DefaultDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.main.TabTextList
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.MeasureActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper

// Requirement Card viewHolder 들의 부모클래스
open class RequirementCardViewHolder(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            buttonLeft.isVisible = false
            tvDueTime.isVisible = false

            data = requirementCard
            tvRequirementToken.text =
                context.getString(R.string.requirement_basic_requirement_token,
                    requirementCard.token)

            setQnaChipGroup(requirementCard)

            buttonRight.isVisible = true
            buttonRight.setText(R.string.requirements_card_view_detail)
            setRightButtonClickListener {
                checkMasterApprovedStatus {
                    context.startActivity(
                        ViewRequirementActivityHelper.getIntent(
                            context,
                            requirementCard.id
                        )
                    )
                }
            }
        }
    }

    private fun setQnaChipGroup(requirementCard: RequirementCard) {
        with(binding.cgQna) {
            this.removeAllViews()       // 데이터 바인딩 과정 중에 생긴 중복칩 삭제
            requirementCard.requirementQnas.let { qnaList ->
                // 너무 많은 데이터를 보여주면 화면이 커지기 때문에, 6개만 추출
                qnaList.take(6).map { qna ->
                    if (!qna.answer.isNullOrEmpty()) (LayoutInflater.from(context)
                        .inflate(R.layout.item_chip_choice_grey_rectangular, this, false) as Chip)
                        .also { chip ->
                            chip.text = qna.answer
                            chip.isClickable = false
                            this.addView(chip)
                        }
                }
            }
        }
    }

    fun checkMasterApprovedStatus(
        function: () -> Unit,
    ) {
        viewModel.masterSimpleInfo.value?.approvedStatus.let {
            when (it) {
                // 미승인 상태이면, 필수정보를 채우도록 이동
                CodeTable.NOT_APPROVED.code ->
                    DefaultDialog.newInstance(
                        DialogData.getAskingFillProfileDialogData(),
                    ).let { dialog ->
                        dialog.setButtonsClickListener(
                            onPositive = {
                                viewModel.setCurrentTab(TabTextList.indexOf(R.string.main_activity_navigation_bar_profile))
                            },
                            onNegative = { }
                        )
                        dialog.show(fragmentManager, dialog.tag)
                    }
                // 승인요청 상태이면, 승인될 때까지 기다리라는 문구
                CodeTable.REQUEST_APPROVE.code ->
                    DefaultDialog.newInstance(
                        DialogData.getWaitingUntilApprovalDialogData()
                    ).let { dialog ->
                        dialog.setButtonsClickListener(
                            onPositive = { },
                            onNegative = { }
                        )
                        dialog.show(fragmentManager, dialog.tag)
                    }
                // 승인 상태이면, 함수 실행
                else -> function()
            }
        }
    }

    fun setCallToClientButton(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            buttonLeft.isVisible = true
            buttonLeft.setText(R.string.call_to_customer)

            setLeftButtonClickListener {
                checkMasterApprovedStatus {
                    DefaultDialog.newInstance(
                        DialogData.getCallToCustomerDialogData()
                    ).let {
                        it.setButtonsClickListener(
                            onPositive = {
                                viewModel.callToClient(requirementId = requirementCard.id)
                                context.startActivity(CallToCustomerHelper.getIntent(requirementCard.phoneNumber))
                            },
                            onNegative = { }
                        )
                        it.show(fragmentManager, it.tag)
                    }
                }
            }
        }
    }

    fun setSendMeasureButton(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            buttonLeft.isVisible = true
            buttonLeft.setText(R.string.send_measure)
            setLeftButtonClickListener {
                checkMasterApprovedStatus {
                    context.startActivity(
                        MeasureActivityHelper.getIntent(
                            context,
                            requirementCard.id
                        )
                    )
                }
            }
        }
    }

    fun setRepairDoneButton(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            buttonLeft.isVisible = true
            buttonLeft.setText(R.string.repair_done_text)
            setLeftButtonClickListener {
                checkMasterApprovedStatus {
                    context.startActivity(
                        EndRepairActivityHelper.getIntent(
                            context,
                            requirementCard.id
                        )
                    )
                }
            }
        }
    }

    fun setRequestReviewButton(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            if (!requirementCard.requestReviewYn) {
                buttonLeft.isVisible = true
                buttonLeft.setText(R.string.request_review)
                setLeftButtonClickListener {
                    checkMasterApprovedStatus {
                        viewModel.askForReview(requirementCard)
                    }
                }
            }
        }
    }

    companion object {
        fun create(
            context: Context,
            fragmentManager: FragmentManager,
            viewModel: RequirementViewModel,
            parent: ViewGroup,
            viewType: Int,
        ): RequirementCardViewHolder {
            val binding = ViewHolderRequirementCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return when (viewType) {
                RequirementStatus.Requested.asInt -> RequestedCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.RequestConsult.asInt -> RequestConsultCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.RequestMeasure.asInt -> RequestMeasureCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.Estimated.asInt -> EstimatedCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.Measuring.asInt -> MeasuringCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.Measured.asInt -> MeasuredCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.Repairing.asInt -> RepairingCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.Done.asInt -> DoneViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.Closed.asInt -> ClosedViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.Canceled.asInt -> CanceledViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                else -> CanceledViewHolder(context, fragmentManager, viewModel, binding)
            }
        }
    }
}

