package kr.co.soogong.master.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kr.co.soogong.master.ui.material.MaterialFragment
import kr.co.soogong.master.ui.mypage.MyPageFragment
import kr.co.soogong.master.ui.profile.ProfileFragment
import kr.co.soogong.master.ui.requirements.RequirementsFragment
import kr.co.soogong.master.ui.user.UserFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    private val numberOfTabs: Int
) : FragmentStatePagerAdapter(fragmentManager, numberOfTabs) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> RequirementsFragment.newInstance()
            1 -> MaterialFragment.newInstance()
            2 -> ProfileFragment.newInstance()
            3 -> MyPageFragment.newInstance()
            else -> Fragment()
        }
    }

    override fun getCount(): Int = numberOfTabs
}