@file:JvmName("TextViewExt")

package kr.co.soogong.master.utility.extension

import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.utility.TimeHelper
import kr.co.soogong.master.utility.TimeHelper.WITHIN_24_HOURS
import kr.co.soogong.master.utility.TimeHelper.WITHIN_90_MINUTES
import java.util.*

@BindingAdapter("dateWithoutDay")
fun TextView.setDateWithoutDay(date: Date?) {
    text = date.formatDateWithoutDay()
}

fun TextView.setEstimationDueTime(createdAt: Date?) {
    text = context.getString(R.string.requirements_card_estimation_due_time,
        TimeHelper.getDueTime(createdAt, WITHIN_24_HOURS))
}

fun TextView.setRequestMeasureDueTime(createdAt: Date?) {
    text = context.getString(R.string.requirements_card_request_measure_due_time,
        TimeHelper.getDueTime(createdAt, WITHIN_90_MINUTES))
}

@BindingAdapter("convertToPrice")
fun TextView.setPrice(price: Int?) {
    price?.let {
        text = price.formatMoney()
    }
}

@BindingAdapter("subscriptionPlan")
fun TextView.setSubscriptionPlan(subscriptionPlan: String?) {
    if (!subscriptionPlan.isNullOrEmpty()) {
        text = CodeTable.getCodeTableByCode(subscriptionPlan)?.inKorean
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
