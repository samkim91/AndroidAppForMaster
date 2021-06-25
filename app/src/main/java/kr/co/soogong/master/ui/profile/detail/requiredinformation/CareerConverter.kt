package kr.co.soogong.master.ui.profile.detail.requiredinformation

import timber.log.Timber
import java.util.*

object CareerConverter {

    fun toCareer(openDate: Date): String {
        val thisYear = Calendar.getInstance(Locale.KOREA).get(Calendar.YEAR)
        val openYear = Calendar.getInstance(Locale.KOREA).apply { time = openDate }.get(Calendar.YEAR)
        val gap = thisYear - openYear

        Timber.tag("CareerConverter").d("toCareer: $openYear to $gap")
        return "${gap}년"
    }


    fun toOpenDate(career: Int): Date {
        val ret = Calendar.getInstance(Locale.KOREA)
        val openYear = ret.get(Calendar.YEAR) - career
        ret.set(openYear, 0, 1)

        Timber.tag("CareerConverter").d("toOpenDate: $career to ${ret.time}")
        return ret.time
    }
}