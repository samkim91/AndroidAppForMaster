@file:JvmName("FormatExt")

package kr.co.soogong.master.utility.extension

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.pow

fun Date?.formatFullDateTime(): String {
    if (this == null) return ""
    return SimpleDateFormat("yyyy.MM.dd(E) - HH:mm", Locale.KOREA).format(this)
}

fun Date?.formatFullDateTimeWithoutDay(): String {
    if (this == null) return ""
    return SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA).format(this)
}

fun Date.formatDateWithDay(): String =
    SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREA).format(this)

fun Date?.formatDateWithoutDay(): String {
    if (this == null) return ""
    return SimpleDateFormat("yyyy. MM. dd", Locale.KOREA).format(this)
}

fun Date?.formatDateWithDash(): String {
    if (this == null) return ""
    return SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(this)
}

fun Int?.formatMoney(): String {
    if (this == null) return "0"
    return "${DecimalFormat("#,###").format(this)}원"
}

fun Long?.formatWon(): String {
    if (this == null || this == 0L) return "0 원"

    val unitWords = listOf("", "만", "억", "조", "경")
    val splitUnit = 10000.0
    val resultArray = mutableListOf<Int>()
    var resultString = ""

    for (index in unitWords.indices) {
        ((this % splitUnit.pow(index + 1)) / splitUnit.pow(index)).also { double ->
            floor(double).toInt().also { int ->
                resultArray.add(if (int > 0) int else -1)
            }
        }
    }

    for (index in resultArray.indices) {
        if (resultArray[index] == -1) continue
        resultString = resultArray[index].formatComma() + unitWords[index] + " " + resultString
    }

    return resultString + "원"
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