package kr.co.soogong.master.ui.requirement.card

import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

@BindingAdapter("setDate")
fun TextView.setCreatedAt(date: Date?) {
    // 2021.01.11 - 13:20
    date?.let {
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
        text = simpleDateFormat.format(it)
    }
}

//@BindingAdapter(value = ["setRequirementStatus", "priceAmount"])
//fun AmountView.setAmount(status: RequirementStatus?, priceAmount: Int?) {
//    title = when (status) {
//        Estimated, Repairing -> {
//            context.getString(R.string.requirements_card_amount_title)
//        }
//        Done, Closed -> {
//            context.getString(R.string.requirements_card_amount_done_title)
//        }
//        else -> {
//            ""
//        }
//    }
//    priceAmount?.let {
//        detail =
//            if (it != 0) "${DecimalFormat("#,###").format(it)}원"
//            else context.getString(R.string.not_estimated_text)
//    }
//}