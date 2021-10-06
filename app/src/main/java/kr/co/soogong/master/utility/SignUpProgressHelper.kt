package kr.co.soogong.master.utility

import kotlin.math.round

object SignUpProgressHelper {
    const val TabCount = 8

    fun getPercentage(currentPage: Int): String {
        return "${round(((currentPage.toDouble() + 1) / TabCount) * 100).toInt()}%"
    }
}
