@file:JvmName("addAdditionInfoViewExt")

package kr.co.soogong.master.utility.extension

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.qna.RequirementQnaDto
import kr.co.soogong.master.ui.widget.AdditionInfoView

fun addAdditionInfoView(
    viewGroup: ViewGroup,
    context: Context,
    list: List<RequirementQnaDto>?,
    description: String?,
    ) {
    viewGroup.removeAllViews()

    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    params.setMargins(0, 12.dp, 0, 12.dp)

    // Qna 추가
    list?.map {
        val view = AdditionInfoView(context)
        view.question = it.question
        view.answer = it.answer

        viewGroup.addView(view, params)
    }

    // description 추가
    description?.let {
        val item = AdditionInfoView(context)
        item.question = context.getString(R.string.view_requirement_description)
        item.answer = it

        viewGroup.addView(item, params)
    }
}