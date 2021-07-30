package kr.co.soogong.master.ui.requirement

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.NotApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.databinding.FragmentRequirementBinding
import kr.co.soogong.master.domain.usecase.auth.GetMasterApprovedStatusUseCase
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uihelper.profile.EditRequiredInformationActivityHelper
import kr.co.soogong.master.uihelper.requirment.RequirementsBadge
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RequirementFragment : BaseFragment<FragmentRequirementBinding>(
    R.layout.fragment_requirement
), RequirementsBadge {

    @Inject
    lateinit var getMasterApprovedStatusUseCase: GetMasterApprovedStatusUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = viewLifecycleOwner

            with(mainViewPager) {
                isUserInputEnabled = false
                adapter = RequirementPagerAdapter(this@RequirementFragment)
                TabLayoutMediator(mainTabs, this) { tab, position ->
                    tab.text = getString(TabTextList[position])
                }.attach()
            }

            bottomViewForFillingProfileInfoContainer.setOnClickListener {
                startActivity(EditRequiredInformationActivityHelper.getIntent(requireContext()))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bind {
            // 필수 정보를 입력하라는 안내를 보여줄지 검사
            bottomViewForFillingProfileInfoContainer.isVisible =
                getMasterApprovedStatusUseCase().let { it == NotApprovedCodeTable.code || it == RequestApproveCodeTable.code }
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