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
            if (input.isEmpty()) return false
            
            val inputInt = input.replace(",", "").toLong()
            return inputInt < Integer.MAX_VALUE
        }
        return (input as Int) < Integer.MAX_VALUE
    }

    fun isPhoneNumberFormat(input: String): Boolean {
        Timber.tag(TAG).d("isPhoneNumberFormat: $input")
        return input.matches("01([0|1|6|7|8|9])?([0-9]{8})".toRegex())
    }

    fun isValidLocalPart(input: String): Boolean {
        return input.matches("([\\w._\\-])*[a-zA-Z0-9]+([\\w._\\-])*([a-zA-Z0-9])+([\\w._\\-])".toRegex())
    }

    fun isValidDomain(input: String): Boolean {
        return input.matches("([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,8}".toRegex())
    }

}