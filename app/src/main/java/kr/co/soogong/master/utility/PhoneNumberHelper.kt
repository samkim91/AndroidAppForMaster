package kr.co.soogong.master.utility

import timber.log.Timber

object PhoneNumberHelper {
    private const val TAG = "PhoneNumberHelper"

    // 01011112222 -> 010-1111-2222
    fun addDashToLocalNumber(phoneNumber: String): String {
        val newPhoneNumber = StringBuilder(phoneNumber)

        if(!newPhoneNumber.contains("-")) {
            newPhoneNumber.insert(3, "-")
            newPhoneNumber.insert(8, "-")
        }

        Timber.tag(TAG).d("addDashToLocalNumber: $phoneNumber to $newPhoneNumber")
        return newPhoneNumber.toString()
    }

    // +82 10-1111-2222 -> 010-1111-2222
    fun toLocalNumber(phoneNumber: String): String {
        if(phoneNumber.contains("+82 10")) {
            return phoneNumber.replace("+82 10", "010")
        }
        return phoneNumber
    }

    // 01011112222 -> +821011112222
    fun toGlobalNumber(phoneNumber: String): String {
        if(phoneNumber.startsWith("010")) {
            return phoneNumber.replace("010", "+8210")
        }
        return phoneNumber
    }
}