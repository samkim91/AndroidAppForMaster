package kr.co.soogong.master.utility.extension

import timber.log.Timber

fun String?.isValidPhoneNumber(): Boolean {
    Timber.tag("ValidationExt").d("isPhoneNumberFormat: $this")
    if (this.isNullOrEmpty()) return false
    return this.matches("01([0|1|6|7|8|9])?([0-9]{8})".toRegex())
}

fun String.isValidLocalPart(): Boolean =
    this.matches("([\\w._\\-])*[a-zA-Z0-9]+([\\w._\\-])*([a-zA-Z0-9])+([\\w._\\-])".toRegex())

fun String.isValidDomain(): Boolean =
    this.matches("([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,8}".toRegex())

fun Any.isIntRange(): Boolean {
    Timber.tag("ValidationExt").d("isIntRange: $this")
    return when (this) {
        is String -> {
            if (this.isEmpty()) return false

            val inputLong = this.replace(",", "").toLong()
            inputLong < Integer.MAX_VALUE
        }
        is Int -> this < Integer.MAX_VALUE
        is Long -> this < Integer.MAX_VALUE
        else -> false
    }
}