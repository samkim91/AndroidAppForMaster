package kr.co.soogong.master.presentation.uihelper.requirment

interface RequirementsBadge {

    fun setReceivedBadge(badgeCount: Int)

    fun setProgressBadge(badgeCount: Int)

    fun setDoneBadge(badgeCount: Int)
}