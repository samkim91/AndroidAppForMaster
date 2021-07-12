@file:JvmName("TextViewExt")

package kr.co.soogong.master.utility.extension

import android.icu.text.DecimalFormat
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bind:to_datetime_string")
fun TextView.setDateTime(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy. MM. dd hh:mm")
    text = simpleDateFormat.format(date ?: System.currentTimeMillis())
}

@BindingAdapter("bind:to_date_string")
fun TextView.setDate(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy. MM. dd")
    text = simpleDateFormat.format(date ?: System.currentTimeMillis())
}

@BindingAdapter("bind:set_price")
fun TextView.setPrice(price: Int?) {
    price?.let {
        text = "${DecimalFormat("#,###").format(it)}원"
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
        if(startIndexOfLink == -1) return
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, (startIndexOfLink + link.first.length),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.movementMethod = LinkMovementMethod.getInstance()
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }
}
