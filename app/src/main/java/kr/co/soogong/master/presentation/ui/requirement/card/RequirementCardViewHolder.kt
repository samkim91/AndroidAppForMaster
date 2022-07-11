package kr.co.soogong.master.presentation.ui.requirement.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.requirement.RequirementCard
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.main.TAB_TEXTS_MAIN_NAVIGATION
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel
import kr.co.soogong.master.presentation.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.MeasureActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.extension.formatDateWithDay
import kr.co.soogong.master.utility.extension.formatMoney

// Requirement Card viewHolder 들의 부모클래스
open class RequirementCardViewHolder(
    protected val context: Context,
    protected val fragmentManager: FragmentManager,
    protected val mainViewModel: MainViewModel,
    protected val viewModel: RequirementsViewModel,
    protected val binding: ViewHolderRequirementCardBinding,
) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            data = requirementCard

            setCardClickListener {
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

    // NOTE: 2022/02/10 삭제된 함수. 추후 참고를 위해 살려둠.
//    private fun setQnaChipGroup(requirementCard: RequirementCard) {
//        with(binding.cgQna) {
//            this.removeAllViews()       // 데이터 바인딩 과정 중에 생긴 중복칩 삭제
//            requirementCard.requirementQnas.let { qnaList ->
//                // 너무 많은 데이터를 보여주면 화면이 커지기 때문에, 6개만 추출
//                qnaList.take(6).map { qna ->
//                    if (!qna.answer.isNullOrEmpty()) (LayoutInflater.from(context)
//                        .inflate(R.layout.item_chip_choice_grey_rectangular, this, false) as Chip)
//                        .also { chip ->
//                            chip.text = qna.answer
//                            chip.isClickable = false
//                            this.addView(chip)
//                        }
//                }
//            }
//        }
//    }

    protected fun setVisitingDate(requirementCard: RequirementCard) {
        with(binding) {
            groupAdditional.isVisible = requirementCard.visitDate != null
            tvAdditionalLabel.text = context.getString(R.string.visiting_date)
            tvAdditionalValue.text = requirementCard.visitDate.formatDateWithDay()
        }
    }

    protected fun setRepairPrice(requirementCard: RequirementCard) {
        with(binding) {
            tvAdditionalLabel.text = context.getString(R.string.repair_actual_price)
            tvAdditionalValue.text = requirementCard.repairPrice.formatMoney()
            groupAdditional.isVisible = true
        }
    }

    private fun checkMasterApprovedStatus(
        function: () -> Unit,
    ) {
        mainViewModel.masterSettings.value?.approvedStatus.let {
            when (it) {
                // 미승인 상태이면, 필수정보를 채우도록 이동
                CodeTable.NOT_APPROVED.code ->
                    DefaultDialog.newInstance(
                        DialogData.getAskingFillRequiredProfile(),
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
                else -> function()
            }
        }
    }

    protected fun AppCompatButton.setAcceptToMeasure(
        requirementCard: RequirementCard,
    ) {
        this.isVisible = true

        this.text = context.getString(R.string.accept_estimation)
        this.background = ResourcesCompat.getDrawable(resources,
            R.drawable.bg_solid_transparent_stroke_green_selector_radius30,
            null)
        this.setTextColor(ResourcesCompat.getColor(resources,
            R.color.selector_green_alpha50,
            null))

        this.setOnClickListener {
            checkMasterApprovedStatus {
                DefaultDialog.newInstance(
                    DialogData.getAcceptEstimation()
                ).let {
                    it.setButtonsClickListener(
                        onPositive = {
                            viewModel.acceptToMeasure(requirementCard)
                        },
                        onNegative = { }
                    )
                    it.show(fragmentManager, it.tag)
                }
            }
        }
    }

    protected fun AppCompatButton.setCallToClient(
        requirementCard: RequirementCard,
    ) {
        this.isVisible = true

        this.text =
            if (requirementCard.contactYn) context.getString(R.string.call_to_customer_again)
            else context.getString(R.string.call_to_customer)

        this.background = ResourcesCompat.getDrawable(resources,
            if (requirementCard.contactYn) R.drawable.bg_solid_transparent_stroke_light_grey2_selector_radius30 else R.drawable.bg_solid_transparent_stroke_green_selector_radius30,
            null)

        this.setTextColor(ResourcesCompat.getColor(resources,
            if (requirementCard.contactYn) R.color.grey_4 else R.color.selector_green_alpha50,
            null))

        this.setOnClickListener {
            checkMasterApprovedStatus {
                DefaultDialog.newInstance(
                    DialogData.getCallToCustomer()
                ).let {
                    it.setButtonsClickListener(
                        onPositive = {
                            viewModel.callToClient(requirementCard)
                        },
                        onNegative = { }
                    )
                    it.show(fragmentManager, it.tag)
                }
            }
        }
    }

    protected fun AppCompatButton.setSendMeasuringDate(
        requirementCard: RequirementCard,
    ) {
        this.isVisible = true
        this.text = context.getString(R.string.insert_visiting_date)
        this.setOnClickListener {
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

    protected fun AppCompatButton.setRepairDone(
        requirementCard: RequirementCard,
    ) {
        this.isVisible = true
        this.text = context.getString(R.string.repair_done_text)
        this.setOnClickListener {
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

    protected fun AppCompatButton.setAskForReview(
        requirementCard: RequirementCard,
    ) {
        this.isVisible = true

        this.setOnClickListener {
            checkMasterApprovedStatus {
                viewModel.askForReview(requirementCard)
            }
        }

        this.isEnabled = !requirementCard.requestReviewYn
        this.text =
            if (!requirementCard.requestReviewYn) context.getString(R.string.request_review) else context.getString(
                R.string.request_review_done)

        this.background = ResourcesCompat.getDrawable(resources,
            if (!requirementCard.requestReviewYn) R.drawable.bg_solid_green_selector_radius30 else R.drawable.bg_solid_light_grey1_selector_radius30,
            null)

        this.setTextColor(ResourcesCompat.getColor(resources,
            if (!requirementCard.requestReviewYn) R.color.white else R.color.grey_1,
            null))
    }

    companion object {
        fun create(
            context: Context,
            fragmentManager: FragmentManager,
            mainViewModel: MainViewModel,
            viewModel: RequirementsViewModel,
            parent: ViewGroup,
            viewType: Int,
        ): RequirementCardViewHolder {
            val binding = ViewHolderRequirementCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return when (viewType) {
                RequirementStatus.RequestMeasure.asInt -> RequestMeasureCardViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)

                RequirementStatus.Measuring.asInt -> MeasuringCardViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)

                RequirementStatus.Repairing.asInt -> RepairingCardViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)

                RequirementStatus.Done.asInt -> DoneViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)

                RequirementStatus.Closed.asInt -> ClosedViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)

                else -> CanceledViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)
            }
        }
    }
}

