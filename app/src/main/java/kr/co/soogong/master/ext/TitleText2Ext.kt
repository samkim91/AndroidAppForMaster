package kr.co.soogong.master.ext

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import kr.co.soogong.master.ui.widget.TitleText2

fun addTextView2(
    viewGroup: ViewGroup,
    context: Context,
    title: String?,
    detail: String?
) {
    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.setMargins(0, 0, 0, 12.dp)

    val view = TitleText2(context)
    view.title = title
    view.detail = detail

    viewGroup.addView(view, params)
}