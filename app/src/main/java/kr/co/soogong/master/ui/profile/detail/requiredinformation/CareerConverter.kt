package kr.co.soogong.master.ui.profile.detail.requiredinformation

import java.util.*

object CareerConverter {

    fun toCareer(openDate: Date): String {
        val today = Date(System.currentTimeMillis())
        val gap = openDate?.compareTo(today)
        return "${gap}년"
    }

    fun toOpenDate(career: Int): Date {
        val today = Calendar.getInstance(Locale.KOREA)
        val openYear = today.get(Calendar.YEAR) - career
        today.set(Calendar.YEAR, openYear)
        return today.time
    }
}