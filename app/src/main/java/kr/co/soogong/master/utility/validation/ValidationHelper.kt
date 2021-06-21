package kr.co.soogong.master.utility.validation

import androidx.core.text.isDigitsOnly
import timber.log.Timber

object ValidationHelper {
    const val TAG = "ValidationHelper"

    fun isNumberOnly(input: String): Boolean {
        Timber.tag(TAG).d("isNumberOnly: $input")
        return input.isDigitsOnly()
    }

    fun isIntRange(input: Any): Boolean {
        Timber.tag(TAG).d("isIntRange: $input")
        if (input is String) {
            val inputInt = input.replace(",", "").toInt()
            return inputInt < Integer.MAX_VALUE
        }
        return (input as Int) < Integer.MAX_VALUE
    }

    fun isPhoneNumberFormat(input: String): Boolean {
        Timber.tag(TAG).d("isPhoneNumberFormat: $input")
        return input.matches("01([0|1|6|7|8|9])?([0-9]{8})".toRegex())
    }
}