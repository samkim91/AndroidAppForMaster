@file:JvmName("ReceivedBindingAdapter")

package kr.co.soogong.master.ui.requirements.received

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bind:receivedList")
fun RecyclerView.setList(items: List<ReceivedCard>?) {
    (adapter as? ReceivedAdapter)?.submitList(items ?: emptyList())
}

@BindingAdapter("bind:received_detail")
fun TextView.setDetailText(userName: String) {
    val value = context.getString(R.string.received_detail_viewholder)

    text = SpannableString("$userName $value").apply {
        setSpan(StyleSpan(Typeface.BOLD), 0, userName.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(RelativeSizeSpan(1.1f), 0, userName.length, 0)
    }
}

@BindingAdapter("bind:received_date")
fun TextView.setDateTime(date: Date) {
    val simpleDateFormat = SimpleDateFormat("yyyy. MM. dd hh:mm")
    text = simpleDateFormat.format(date)
}
