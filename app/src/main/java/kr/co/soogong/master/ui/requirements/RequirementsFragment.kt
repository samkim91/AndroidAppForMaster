package kr.co.soogong.master.ui.requirements

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsBinding
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

class RequirementsFragment : BaseFragment<FragmentRequirementsBinding>(
    R.layout.fragment_requirements
), RequirementsBadge {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = viewLifecycleOwner

            with(actionBar) {
                title.text = getString(R.string.main_page_request_name)
            }

            with(mainViewPager) {
                isUserInputEnabled = false
                adapter = RequirementsPagerAdapter(requireActivity())
                TabLayoutMediator(mainTabs, this) { tab, position ->
                    tab.text = getString(TabTextList[position])
                }.attach()
            }
        }
    }

    override fun unsetProgressBadge() {
        binding.mainTabs.getTabAt(1)?.removeBadge()
    }

    override fun setProgressBadge(badgeCount: Int) {
        val badge = binding.mainTabs.getTabAt(1)?.orCreateBadge
        badge?.backgroundColor = resources.getColor(R.color.app_color, null)
        badge?.number = badgeCount
    }

    override fun unsetReceivedBadge() {
        binding.mainTabs.getTabAt(0)?.removeBadge()
    }

    override fun setReceivedBadge(badgeCount: Int) {
        val badge = binding.mainTabs.getTabAt(0)?.orCreateBadge
        badge?.backgroundColor = resources.getColor(R.color.app_color, null)
        badge?.number = badgeCount
    }

    companion object {
        private const val TAG = "RequirementsFragment"

        fun newInstance(): RequirementsFragment {
            return RequirementsFragment()
        }
    }
}