package kr.co.soogong.master.ui.requirements.received

import kr.co.soogong.master.data.requirements.Requirement
import java.util.*

data class ReceivedCard(
    val id: String,
    val category: String,
    val location: String,
    val date: Date,
    val userName: String
) {
    companion object {
        fun from(requirement: Requirement) = ReceivedCard(
            id = requirement.id,
            category = requirement.category,
            location = requirement.location,
            date = requirement.date,
            userName = requirement.userName
        )
    }
}