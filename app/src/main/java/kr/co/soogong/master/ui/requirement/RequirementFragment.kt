package kr.co.soogong.master.ui.requirement

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.NotApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.databinding.FragmentRequirementBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uihelper.profile.EditRequiredInformationActivityHelper
import kr.co.soogong.master.uihelper.requirment.RequirementsBadge
import kr.co.soogong.master.uihelper.requirment.action.SearchActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class RequirementFragment : BaseFragment<FragmentRequirementBinding>(
    R.layout.fragment_requirement
), RequirementsBadge {

    private val viewModel: RequirementViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = viewLifecycleOwner

            with(requirementsViewPager) {
                isUserInputEnabled = false
                adapter = RequirementPagerAdapter(this@RequirementFragment)
                TabLayoutMediator(mainTabs, this) { tab, position ->
                    tab.text = getString(TabTextList[position])
                }.attach()
            }

            searchBar.setSearchEditTextClickListener {
                startActivity(SearchActivityHelper.getIntent(requireContext()))
            }

            bottomView.container.setOnClickListener {
                startActivity(EditRequiredInformationActivityHelper.getIntent(requireContext()))
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.masterSimpleInfo.observe(viewLifecycleOwner, { masterDto ->
            binding.bottomView.root.isVisible =
                masterDto.approvedStatus == NotApprovedCodeTable.code || masterDto.approvedStatus == RequestApproveCodeTable.code
        })
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        bind {
            // 필수 정보를 입력하라는 bottom view 를 보여줄지 결정
            viewModel.requestMasterSimpleInfo()
        }
    }

    override fun setReceivedBadge(badgeCount: Int) {
        val tab = binding.mainTabs.getTabAt(0)
        if (badgeCount > 0) {
            visibleBadge(tab, badgeCount)
        } else {
            invisibleBadge(tab)
        }
    }

    override fun setProgressBadge(badgeCount: Int) {
        val tab = binding.mainTabs.getTabAt(1)
        if (badgeCount > 0) {
            visibleBadge(tab, badgeCount)
        } else {
            invisibleBadge(tab)
        }
    }

    override fun setDoneBadge(badgeCount: Int) {
        val tab = binding.mainTabs.getTabAt(2)
        if (badgeCount > 0) {
            visibleBadge(tab, badgeCount)
        } else {
            invisibleBadge(tab)
        }
    }

    private fun visibleBadge(tab: TabLayout.Tab?, badgeCount: Int) {
        tab?.orCreateBadge?.number = badgeCount
        tab?.badge?.badgeTextColor = resources.getColor(R.color.color_FFFFFF, null)
    }

    private fun invisibleBadge(tab: TabLayout.Tab?) {
        tab?.removeBadge()
    }

    companion object {
        private const val TAG = "RequirementsFragment"

        fun newInstance(): RequirementFragment {
            return RequirementFragment()
        }
    }
}