package kr.co.soogong.master.presentation.uihelper.main

interface MainBadge {
    fun setRequirementsBadge(badgeCount: Int)

    fun unsetRequirementsBadge()

    fun setProfileBadge(badgeCount: Int)

    fun unsetProfileBadge()

    fun setMyPageBadge(badgeCount: Int)

    fun unsetMyPageBadge()
}