@file:JvmName("ProgressBindingAdapter")

package kr.co.soogong.master.ui.requirements.progress

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

@BindingAdapter("bind:progressList")
fun RecyclerView.setList(items: List<ProgressCard>?) {
    (adapter as? ProgressAdapter)?.submitList(items ?: emptyList())
}

@BindingAdapter("bind:progress_Title")
fun TextView.setDetailText(list: List<ProgressCard>?) {
    val value = context.getString(R.string.noti_progress_fragment)
    val total = "총 ${list?.size ?: 0}건"
    text = SpannableString("$total$value").apply {
        setSpan(StyleSpan(Typeface.BOLD), 0, total.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(RelativeSizeSpan(1.1f), 0, total.length, 0)
    }
}

@BindingAdapter("bind:progress_detail")
fun TextView.setDetailText(userName: String) {
    val value = context.getString(R.string.progress_detail_viewholder)

    text = SpannableString("$userName $value").apply {
        setSpan(StyleSpan(Typeface.BOLD), 0, userName.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(RelativeSizeSpan(1.1f), 0, userName.length, 0)
    }
}

@BindingAdapter("bind:progress_date")
fun TextView.setDateTime(date: Date) {
    val simpleDateFormat = SimpleDateFormat("yyyy. MM. dd hh:mm")
    text = simpleDateFormat.format(date)
}