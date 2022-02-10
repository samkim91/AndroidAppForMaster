package kr.co.soogong.master.presentation.ui.requirement.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.domain.entity.requirement.RequirementCard
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.main.checkMasterApprovedStatus
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel
import kr.co.soogong.master.presentation.uihelper.requirment.action.ViewRequirementActivityHelper
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
                checkMasterApprovedStatus(fragmentManager, mainViewModel) {
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

    fun setEstimationPrice(estimationPrice: Int) {
        with(binding) {
            tvPriceLabel.text = context.getString(R.string.my_estimation_price)
            tvPrice.text = estimationPrice.formatMoney()
            groupPrice.isVisible = true
        }
    }

    fun setRepairPrice(actualPrice: Int) {
        with(binding) {
            tvPriceLabel.text = context.getString(R.string.repair_actual_price)
            tvPrice.text = actualPrice.formatMoney()
            groupPrice.isVisible = true
        }
    }

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
                RequirementStatus.Requested.asInt -> RequestedCardViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)

                RequirementStatus.RequestConsult.asInt -> RequestConsultCardViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)

                RequirementStatus.RequestMeasure.asInt -> RequestMeasureCardViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)

                RequirementStatus.Estimated.asInt -> EstimatedCardViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)

                RequirementStatus.Measuring.asInt -> MeasuringCardViewHolder(context,
                    fragmentManager,
                    mainViewModel,
                    viewModel,
                    binding)

                RequirementStatus.Measured.asInt -> MeasuredCardViewHolder(context,
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

                RequirementStatus.Canceled.asInt -> CanceledViewHolder(context,
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

