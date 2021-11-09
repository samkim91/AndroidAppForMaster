package kr.co.soogong.master.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.ui.home.HomeFragment
import kr.co.soogong.master.ui.mypage.MyPageFragment
import kr.co.soogong.master.ui.profile.ProfileFragment
import kr.co.soogong.master.ui.requirement.RequirementFragment
import timber.log.Timber

class MainPagerAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = TabCount

    override fun createFragment(position: Int): Fragment {
        Timber.tag(TAG).d("createFragment: $position")
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> RequirementFragment.newInstance()
            2 -> ProfileFragment.newInstance()
            3 -> MyPageFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }

    companion object {
        private const val TAG = "MainPagerAdapter"
    }
}