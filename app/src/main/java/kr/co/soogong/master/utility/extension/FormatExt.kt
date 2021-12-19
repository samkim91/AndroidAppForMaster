@file:JvmName("FormatExt")

package kr.co.soogong.master.utility.extension

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import java.util.*

fun Date?.formatFullDateTime(): String {
    if (this == null) return ""
    return SimpleDateFormat("yyyy.MM.dd(E) - HH:mm", Locale.KOREA).format(this)
}

fun Date?.formatFullDateTimeWithoutDay(): String {
    if (this == null) return ""
    return SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA).format(this)
}

fun Date?.formatDate(): String {
    if (this == null) return ""
    return SimpleDateFormat("yyyy.MM.dd(E)", Locale.KOREA).format(this)
}

fun Date?.formatDateWithoutDay(): String {
    if (this == null) return ""
    return SimpleDateFormat("yyyy. MM. dd", Locale.KOREA).format(this)
}

fun Int?.formatMoney(): String {
    if (this == null) return "0"
    return "${DecimalFormat("#,###").format(this)}원"
}

fun Int?.formatComma(): String {
    if (this == null) return "0"
    return DecimalFormat("#,###").format(this)
}

fun Long?.formatComma(): String {
    if (this == null) return "0"
    return DecimalFormat("#,###").format(this)
}

fun String?.exceptComma(): String {
    if (this.isNullOrEmpty()) return "0"
    return this.replace(",", "")
}

fun Double?.formatDecimal(): String {
    if (this == null) return "0.0"
    return DecimalFormat("#.#").format(this)
}