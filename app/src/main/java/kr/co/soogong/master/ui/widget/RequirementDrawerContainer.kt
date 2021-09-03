package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.profile.Review
import kr.co.soogong.master.data.model.requirement.repair.CanceledReason
import kr.co.soogong.master.databinding.ViewRequirementDrawerContainerBinding
import kr.co.soogong.master.ui.profile.review.ReviewViewHolderHelper
import kr.co.soogong.master.uihelper.requirment.action.CancelActivityHelper
import kr.co.soogong.master.utility.extension.dp
import kr.co.soogong.master.utility.extension.startHalfRotateAnimation

class RequirementDrawerContainer @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementDrawerContainerBinding.inflate(LayoutInflater.from(context), this, true)

    var label: String? = ""
        set(value) {
            field = value
            with(binding) {
                drawerLabel.text = value
            }
        }

    var detailContainerVisibility: Boolean = false
        set(value) {
            field = value
            with(binding) {
                detailContainer.isVisible = value
                drawerLabel.setOnClickListener {
                    drawerIcon.startHalfRotateAnimation(!detailContainer.isVisible)
                    detailContainer.isVisible = !detailContainer.isVisible
                }
            }
        }

    val detailContainer = binding.detailContainer

    companion object {
        const val REQUIREMENT_TYPE = 100
        const val ESTIMATION_TYPE = 200
        const val REPAIR_TYPE = 300
        const val REVIEW_TYPE = 400
        const val CANCEL_TYPE = 500

        fun addDrawerContainer(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
            contentType: Int,
            isSpread: Boolean,
            includingCancel: Boolean,
        ) {
            RequirementDrawerContainer(context).apply {
                when (contentType) {
                    REQUIREMENT_TYPE -> {
                        label =
                            context.getString(R.string.requirement_drawer_title_of_customer_request)
                        RequirementQna.addRequirementQna(
                            context,
                            detailContainer,
                            requirementDto,
                            includingCancel
                        )
                    }
                    ESTIMATION_TYPE -> {
                        label =
                            context.getString(R.string.requirement_drawer_title_of_my_estimation)
                        EstimationDetail.addEstimationDetail(
                            context,
                            detailContainer,
                            requirementDto,
                            includingCancel
                        )
                    }
                    REPAIR_TYPE -> {
                        label =
                            context.getString(R.string.requirement_drawer_title_of_repair)
                        EstimationDetail.addEstimationDetail(
                            context,
                            detailContainer,
                            requirementDto,
                            includingCancel
                        )
                    }
                    CANCEL_TYPE -> {
                        label =
                            context.getString(R.string.requirement_drawer_title_of_canceled_reason)
                        addCanceledDetail(
                            context,
                            detailContainer,
                            requirementDto
                        )
                    }
                    REVIEW_TYPE -> {
                        label =
                            context.getString(R.string.requirement_drawer_title_of_customer_review)
                        addReviewDetail(
                            context,
                            detailContainer,
                            requirementDto
                        )
                    }

                }
                detailContainerVisibility = isSpread
            }.run {
                container.addView(this)
            }
        }

        private fun addCanceledDetail(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto
        ) {
            requirementDto.let { requirement ->
                requirement.canceledYn?.let { isCanceled ->
                    if (isCanceled) {
                        EstimationDetail(context).apply {
                            key = context.getString(R.string.cancel_repair_title)
                            value =
                                CanceledReason.getCanceledReasonFromCode(requirement.canceledCode).inKorean
                            bold = true
                        }.run {
                            container.addView(this)
                        }

                        EstimationDetail(context).apply {
                            key = context.getString(R.string.description_label)
                            value = requirement.canceledDescription
                        }.run {
                            container.addView(this)
                        }
                    }
                }
            }
        }

        private fun addReviewDetail(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto
        ) {
            requirementDto.estimationDto?.repair?.review?.let {
                ReviewViewHolderHelper.create(container).run {
                    binding(context, Review.fromReviewDto(it))
                    this.itemView.layoutParams.let {
                        it as MarginLayoutParams
                        it.topMargin = 12.dp
                    }
                    container.addView(this.itemView)
                }
            }
        }

        fun addCancelButton(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto
        ) {
            val params = LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 12.dp, 0, 12.dp)
            }

            val textView = TextView(context).apply {
                text = context.getString(R.string.cancel_requirement_text)
                gravity = Gravity.CENTER
                setTextColor(context.getColor(R.color.color_FF711D))
                setTextAppearance(R.style.text_style_16sp_bold)
                setOnClickListener {
                    context.startActivity(
                        CancelActivityHelper.getIntent(
                            context,
                            requirementDto.id
                        )
                    )
                }
            }

            container.addView(textView, params)
        }
    }
}