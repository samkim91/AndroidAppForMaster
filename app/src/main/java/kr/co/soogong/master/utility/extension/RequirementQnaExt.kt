@file:JvmName("RequirementQnaExt")

package kr.co.soogong.master.utility.extension

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.qna.RequirementQnaDto
import kr.co.soogong.master.ui.widget.RequirementImages
import kr.co.soogong.master.ui.widget.RequirementQna

fun addRequirementQna(
    viewGroup: ViewGroup,
    context: Context,
    requirementDto: RequirementDto,
    ) {
    viewGroup.removeAllViews()

    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    params.setMargins(0, 12.dp, 0, 12.dp)

    // Qna 추가
    requirementDto.requirementQnas?.let { list ->
        list.map {
            val item = RequirementQna(context)
            item.question = it.question
            item.answer = it.answer

            viewGroup.addView(item, params)
        }
    }

    // description 추가
    requirementDto.description?.let {
        val item = RequirementQna(context)
        item.question = context.getString(R.string.view_requirement_description)
        item.answer = it

        viewGroup.addView(item, params)
    }

    // image 추가
    requirementDto.images?.let {
        val item = RequirementImages(context).apply {
            images = it
        }

        viewGroup.addView(item, params)
    }
}