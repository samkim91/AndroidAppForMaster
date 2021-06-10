package kr.co.soogong.master.utility.validation

import androidx.core.text.isDigitsOnly
import timber.log.Timber

object ValidationHelper {
    const val TAG = "ValidationHelper"

    fun isNumberOnly(input: String): Boolean {
        Timber.tag(TAG).d("isNumberOnly: $input")
        return input.isDigitsOnly()
    }
}