package kr.co.soogong.master.ui.requirements

interface RequirementsBadge {

    fun unsetProgressBadge()

    fun setProgressBadge(badgeCount: Int)

    fun unsetReceivedBadge()

    fun setReceivedBadge(badgeCount: Int)
}