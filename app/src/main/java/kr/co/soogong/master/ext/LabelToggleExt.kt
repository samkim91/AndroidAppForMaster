package kr.co.soogong.master.ext

import android.content.Context
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import com.nex3z.togglebuttongroup.button.LabelToggle
import kr.co.soogong.master.R

fun createLabelToggle(
    context: Context,
    label: String,
    checked: Boolean = false,
    clickable: Boolean = true
): View {
    val contextWrapper =
        ContextThemeWrapper(context, R.style.small_text_style_medium)
    val toggle = LabelToggle(contextWrapper)

    with(toggle) {
        text = label
        markerColor = context.getColor(R.color.app_color)
        setTextColor(context.getColorStateList(R.color.date_selector))
        isChecked = checked
        isClickable = clickable
    }
    return toggle
}