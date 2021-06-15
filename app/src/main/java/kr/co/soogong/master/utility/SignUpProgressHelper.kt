package kr.co.soogong.master.utility

import kr.co.soogong.master.ui.auth.signup.TabCount
import kotlin.math.round

object SignUpProgressHelper {
    operator fun invoke(currentPage: Int): String {
        return "${round(((currentPage.toDouble() + 1) / TabCount) * 100).toInt()}%"
    }
}
