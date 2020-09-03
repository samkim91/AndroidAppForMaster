@file:JvmName("ReceivedBindingAdapter")

package kr.co.soogong.master.ui.requirements.received

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bind:receivedList")
fun RecyclerView.setList(items: List<ReceivedCard>?) {
    (adapter as? ReceivedAdapter)?.submitList(items ?: emptyList())
}

@BindingAdapter("bind:received_detail")
fun TextView.setDetailText(userName: String) {
    val value = context.getString(
        R.string.received_detail_viewholder,
        userName
    )
    Timber.tag("EXT").d("setDetailText: $value")

    text = HtmlCompat.fromHtml(value, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

@BindingAdapter("bind:received_date")
fun TextView.setDateTime(date: Date) {
    val simpleDateFormat = SimpleDateFormat("yyyy. MM. dd hh:mm")
    text = simpleDateFormat.format(date)
}
