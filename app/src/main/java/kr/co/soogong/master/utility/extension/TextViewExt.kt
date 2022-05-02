@file:JvmName("TextViewExt")

package kr.co.soogong.master.utility.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.domain.entity.common.LabelTheme
import java.util.*

@BindingAdapter("dateWithoutDay")
fun TextView.setDateWithoutDay(date: Date?) {
    text = date.formatDateWithoutDay()
}

@BindingAdapter("dateWithDay")
fun TextView.setDateWithDay(date: Date?) {
    text = date.formatDateWithDay()
}

@BindingAdapter("timeWithMeridiem")
fun TextView.setTimeWithMeridiem(date: Date?) {
    text = date.formatTime()
}

@BindingAdapter("convertToPrice")
fun TextView.setPrice(price: Int?) {
    price?.let {
        text = price.formatMoney()
    }
}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1

    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        if (startIndexOfLink == -1) return
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, (startIndexOfLink + link.first.length),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.movementMethod = LinkMovementMethod.getInstance()
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }
}

@BindingAdapter("labelTheme")
fun TextView.setLabelTheme(labelTheme: LabelTheme) {
    this.background = ResourcesCompat.getDrawable(resources, labelTheme.backgroundRes, null)
    this.backgroundTintList = ResourcesCompat.getColorStateList(resources, labelTheme.backgroundTintRes, null)
    this.setTextColor(ResourcesCompat.getColor(resources, labelTheme.textColorRes, null))
    this.background.alpha = labelTheme.backgroundTintAlpha
}

fun TextView.setUnderline() {
    SpannableString(this.text).let { spannableString ->
        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)
        this.text = spannableString
    }
}

fun TextView.setCopyToClipboard() {
    this.setOnClickListener {
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
            ClipData.newPlainText("주소", this.text)
        )

        context.toast(context.getString(R.string.address_copied_successfully))
    }
}
