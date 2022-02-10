package kr.co.soogong.master.utility

import android.icu.util.Calendar
import timber.log.Timber
import java.util.*

// NOTE: 2022/02/09 필요 여부에 따라서 리팩토링 필요
object TimeHelper {
    const val TAG = "TimeHelper"

    const val WITHIN_24_HOURS = 100
    const val WITHIN_90_MINUTES = 200

    fun getDueTime(createdAt: Date?, condition: Int): String {
        if (createdAt == null) return ""

        val dueTime = Calendar.getInstance(Locale.KOREA).apply {
            time = createdAt
            when (condition) {
                WITHIN_24_HOURS -> add(Calendar.DATE, 1)
                WITHIN_90_MINUTES -> add(Calendar.MINUTE, 90)
            }
        }

        val gap = dueTime.time.time - Calendar.getInstance(Locale.KOREA).time.time

        val minutes = gap / 1000 / 60
        val hours = minutes / 60
        val leftMinutes = minutes % 60

        Timber.tag(TAG).d("getDueTime: $hours : $leftMinutes")

        if (hours < 0.0 || leftMinutes < 0.0) return "만료"

        return "${hours}시간 ${leftMinutes}분 남음"
    }
}