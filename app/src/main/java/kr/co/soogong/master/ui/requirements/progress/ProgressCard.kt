package kr.co.soogong.master.ui.requirements.progress

import kr.co.soogong.master.data.requirements.Requirement
import java.util.*

data class ProgressCard(
    val keycode: String,
    val category: String,
    val location: String,
    val date: Date,
    val tel: String,
    val content: String,
    val wantedDate: String = "",
    val detail: String = ""
) {
    companion object {
        fun from(requirement: Requirement) = ProgressCard(
            keycode = requirement.keycode,
            category = requirement.category,
            location = requirement.location,
            date = requirement.date,
            tel = requirement.tel,
            content = requirement.content
        )
    }
}