package kr.co.soogong.master.uiinterface.requirments

interface RequirementsBadge {

    fun setReceivedBadge(badgeCount: Int)

    fun unsetReceivedBadge()

    fun setProgressBadge(badgeCount: Int)

    fun unsetProgressBadge()

    fun setDoneBadge(badgeCount: Int)

    fun unsetDoneBadge()
}