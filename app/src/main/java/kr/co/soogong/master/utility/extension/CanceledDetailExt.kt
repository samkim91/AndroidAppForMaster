@file:JvmName("CanceledDetailExt")

package kr.co.soogong.master.utility.extension

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.repair.RepairCanceledReason
import kr.co.soogong.master.ui.widget.EstimationDetail

fun addCanceledDetail(
    viewGroup: ViewGroup,
    context: Context,
    requirementDto: RequirementDto
) {
    viewGroup.removeAllViews()

    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(0, 16.dp, 0, 0)
    }

    requirementDto.let { requirement ->
        requirement.canceledYn?.let { isCanceled ->
            if (isCanceled) {
                val view = EstimationDetail(context)
                view.key = context.getString(R.string.cancel_repair_title)
                view.value =
                    RepairCanceledReason.getCanceledReason(requirement.canceledCode).inKorean
                view.bold = true

                viewGroup.addView(view, params)

                val viewSecond = EstimationDetail(context)
                viewSecond.key = context.getString(R.string.description_label)
                viewSecond.value = requirement.canceledDescription

                viewGroup.addView(viewSecond, params)
            }
        }
    }
}