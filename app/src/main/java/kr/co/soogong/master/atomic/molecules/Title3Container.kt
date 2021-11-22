package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.profile.CodeTable
import kr.co.soogong.master.databinding.ViewIconLabelContainerBinding
import kr.co.soogong.master.utility.extension.dp

class Title3Container @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewIconLabelContainerBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) binding.tvContainerTitle.text = value
        }

    val detailContainer: LinearLayoutCompat = binding.llcDetailContainer

    companion object {
        const val REQUIREMENT_TYPE = 100
        const val ESTIMATION_TYPE = 200
        const val PREVIOUS_ESTIMATION_TYPE = 201
        const val REPAIR_TYPE = 300
        const val REVIEW_TYPE = 400
        const val CANCEL_TYPE = 500

        fun addIconLabelContainer(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
            contentType: Int,
        ) {
            Title3Container(context).also { item ->
                when (contentType) {
                    REQUIREMENT_TYPE -> {
                        item.title =
                            context.getString(R.string.view_requirement_customer_request_label)
                        SubheadlineContentTag.addRequirementQnas(
                            context,
                            item.detailContainer,
                            requirementDto,
                        )
                    }
                    PREVIOUS_ESTIMATION_TYPE -> {
                        item.title =
                            context.getString(R.string.view_requirement_previous_estimation_label)
                        SubheadlineContentTag.addPreviousEstimation(
                            context,
                            item.detailContainer,
                            requirementDto
                        )
                    }
                    ESTIMATION_TYPE -> {
                        item.title =
                            if (CodeTable.isSecretaryRequirement(requirementDto.typeCode))
                                context.getString(R.string.view_requirement_my_measurement_label)
                            else
                                context.getString(R.string.view_requirement_my_estimation_label)
                        SubheadlineContentTag.addEstimationDetail(
                            context,
                            item.detailContainer,
                            requirementDto,
                        )
                    }
                    REPAIR_TYPE -> {
                        item.title =
                            context.getString(R.string.view_requirement_my_repair_label)
                        SubheadlineContentTag.addDoneDetail(
                            context,
                            item.detailContainer,
                            requirementDto
                        )
                    }
                    CANCEL_TYPE -> {
                        item.title =
                            context.getString(R.string.view_requirement_canceled_reason_label)
                        SubheadlineContentTag.addCanceledReason(
                            context,
                            item.detailContainer,
                            requirementDto
                        )
                    }
                    REVIEW_TYPE -> {
                        item.title =
                            context.getString(R.string.view_requirement_customer_review_label)
                        SubheadlineContentTag.addReviewDetail(
                            context,
                            item.detailContainer,
                            requirementDto
                        )
                    }
                }
                container.addView(
                    item,
                    LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT).apply { topMargin = 24.dp }
                )
            }
        }
    }
}