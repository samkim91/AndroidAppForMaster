package kr.co.soogong.master.ui.profile.detail.requiredinformation

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

object CareerConverter {

    fun toCareer(openDate: String): String {
        val startDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).parse(openDate)!!

        val thisYear = Calendar.getInstance(Locale.KOREA).get(Calendar.YEAR)
        val openYear = Calendar.getInstance(Locale.KOREA).apply { time = startDate }.get(Calendar.YEAR)
        val gap = thisYear - openYear

        Timber.tag("CareerConverter").d("toCareer: $openYear to $gap")
        return "${gap}년"
    }

    fun toOpenDate(career: Int): String {
        val cal = Calendar.getInstance(Locale.KOREA)
        val openYear = cal.get(Calendar.YEAR) - career
        cal.set(openYear, 0, 1)

        Timber.tag("CareerConverter").d("toOpenDate: $career to ${cal.time}")
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(cal.time)
    }
}