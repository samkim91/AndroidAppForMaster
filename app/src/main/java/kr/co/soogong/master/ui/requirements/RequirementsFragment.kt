package kr.co.soogong.master.ui.requirements

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsBinding
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

class RequirementsFragment : BaseFragment<FragmentRequirementsBinding>(
    R.layout.fragment_requirements
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
    }

    private fun initLayout() {
        bind {
            with(mainTabs) {
                addTab(newTab().setText("견적 대기"))
                addTab(newTab().setText("진행중"))
                tabGravity = TabLayout.GRAVITY_FILL

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab) = Unit

                    override fun onTabUnselected(tab: TabLayout.Tab) = Unit

                    override fun onTabSelected(tab: TabLayout.Tab) {
                        mainViewPager.currentItem = tab.position
                    }
                })
            }

            with(mainViewPager) {
                adapter = RequirementsPagerAdapter(childFragmentManager, mainTabs.tabCount)

                addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mainTabs))

                swipEnabled = false

                currentItem = 0
            }
        }
    }

    companion object {
        private const val TAG = "RequirementsFragment"

        fun newInstance(): RequirementsFragment {
            return RequirementsFragment()
        }
    }
}