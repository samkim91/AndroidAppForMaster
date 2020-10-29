package kr.co.soogong.master.ui.requirements.received

import kr.co.soogong.master.data.requirements.Requirement
import java.util.*

data class ReceivedCard(
    val keycode: String,
    val project: String,
    val date: Date,
    val location: String,
    val content: String,
    val wantedDate: String = "",
    val detail: String = ""
) {
    companion object {
        private const val TAG = "ReceivedCard"

        fun from(requirement: Requirement) = ReceivedCard(
            keycode = requirement.keycode,
            project = requirement.project,
            location = requirement.location,
            date = requirement.date,
            content = requirement.content
        )
    }
}