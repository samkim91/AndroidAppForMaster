package kr.co.soogong.master.ui.requirements

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsBinding
import kr.co.soogong.master.domain.usecase.GetMasterApprovalUseCase
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.main.MainActivity
import kr.co.soogong.master.uiinterface.requirments.RequirementsBadge
import kr.co.soogong.master.util.extension.visible
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RequirementsFragment : BaseFragment<FragmentRequirementsBinding>(
    R.layout.fragment_requirements
), RequirementsBadge {

    @Inject
    lateinit var getMasterApprovalUseCase: GetMasterApprovalUseCase

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
                adapter = RequirementsPagerAdapter(this@RequirementsFragment)
                TabLayoutMediator(mainTabs, this) { tab, position ->
                    tab.text = getString(TabTextList[position])
                }.attach()
            }

            // 필수 정보를 입력하라는 안내
            if(getMasterApprovalUseCase()!!) bottomViewForFillingProfileInfoContainer.visible(false) else bottomViewForFillingProfileInfoContainer.visible(true)
            bottomViewForFillingProfileInfoContainer.setOnClickListener {
                // Todo.. 필수 정보 등록 액티비티로 이동하기
            }
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

        fun newInstance(): RequirementsFragment {
            return RequirementsFragment()
        }
    }
}