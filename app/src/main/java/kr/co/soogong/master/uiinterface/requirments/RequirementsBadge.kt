package kr.co.soogong.master.uiinterface.requirments

interface RequirementsBadge {

    fun setReceivedBadge(badgeCount: Int)

    fun setProgressBadge(badgeCount: Int)

    fun setDoneBadge(badgeCount: Int)
}