package kr.co.soogong.master.ui.requirements.progress

import kr.co.soogong.master.data.requirements.Requirement
import java.util.*

data class ProgressCard(
    val id: Long,
    val category: String,
    val location: String,
    val date: Date,
    val userName: String,
    val tel: String
) {
    companion object {
        fun from(requirement: Requirement) = ProgressCard(
            id = requirement.id,
            category = requirement.category,
            location = requirement.location,
            date = requirement.date,
            userName = requirement.userName,
            tel = requirement.tel
        )

        val EMPTY_CARD = ProgressCard(-1, "", "", Date(0), "", "")
    }
}