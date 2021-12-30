package kr.co.soogong.master.utility.extension

import timber.log.Timber

fun String?.isValidPhoneNumber(): Boolean {
    Timber.tag("ValidationExt").d("isPhoneNumberFormat: $this")
    if (this.isNullOrEmpty()) return false
    return this.matches("01([0|1|6|7|8|9])?([0-9]{8})".toRegex())
}