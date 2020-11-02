package kr.co.soogong.master.ext

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import kr.co.soogong.master.ui.widget.TitleText3

fun addTextView3(
    viewGroup: ViewGroup,
    context: Context,
    title: String?,
    detail: String?
) {
    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.setMargins(0, 0, 0, 8.dp)

    val view = TitleText3(context)
    view.title = title
    view.detail = detail

    viewGroup.addView(view, params)
}