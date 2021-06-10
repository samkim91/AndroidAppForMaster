package kr.co.soogong.master.uihelper.requirment

interface RequirementsBadge {

    fun setReceivedBadge(badgeCount: Int)

    fun setProgressBadge(badgeCount: Int)

    fun setDoneBadge(badgeCount: Int)
}