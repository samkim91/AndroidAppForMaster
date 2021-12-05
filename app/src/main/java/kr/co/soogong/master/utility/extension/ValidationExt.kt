package kr.co.soogong.master.utility.extension

import kr.co.soogong.master.utility.validation.ValidationHelper
import timber.log.Timber
import java.util.*

fun String?.isValidPhoneNumber(): Boolean {
    Timber.tag(ValidationHelper.TAG).d("isPhoneNumberFormat: $this")
    if (this.isNullOrBlank()) return false
    return this.matches("01([0|1|6|7|8|9])?([0-9]{8})".toRegex())
}