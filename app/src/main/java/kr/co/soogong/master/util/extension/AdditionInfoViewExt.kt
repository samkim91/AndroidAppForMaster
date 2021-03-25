@file:JvmName("addAdditionInfoViewExt")

package kr.co.soogong.master.util.extension

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import kr.co.soogong.master.ui.widget.AdditionInfoView

fun addAdditionInfoView(
    viewGroup: ViewGroup,
    context: Context,
    question: String?,
    answer: String?
) {
    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    params.setMargins(0, 12.dp, 0, 12.dp)

    val view = AdditionInfoView(context)
    view.question = question
    view.answer = answer

    viewGroup.addView(view, params)
}