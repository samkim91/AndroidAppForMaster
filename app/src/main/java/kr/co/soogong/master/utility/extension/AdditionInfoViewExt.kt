@file:JvmName("addAdditionInfoViewExt")

package kr.co.soogong.master.utility.extension

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import kr.co.soogong.master.data.dto.requirement.qna.RequirementQnaDto
import kr.co.soogong.master.ui.widget.AdditionInfoView

fun addAdditionInfoView(
    viewGroup: ViewGroup,
    context: Context,
    list: List<RequirementQnaDto>,
) {
    viewGroup.removeAllViews()

    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    params.setMargins(0, 12.dp, 0, 12.dp)

    list.map {
        val view = AdditionInfoView(context)
        view.question = it.question
        view.answer = it.answer

        viewGroup.addView(view, params)
    }
}