package kr.co.soogong.master.ui.requirements.progress.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kr.co.soogong.master.ui.requirements.progress.detail.estimate.ProgressEstimateFragment
import kr.co.soogong.master.ui.requirements.progress.detail.request.ProgressRequestFragment

class ProgressPagerAdapter(
    fragmentManager: FragmentManager,
    private val numberOfTabs: Int,
    private val keycode: String
) : FragmentStatePagerAdapter(fragmentManager, numberOfTabs) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ProgressRequestFragment.newInstance(keycode)
            1 -> ProgressEstimateFragment.newInstance(keycode)
            else -> Fragment()
        }
    }

    override fun getCount(): Int = numberOfTabs

}