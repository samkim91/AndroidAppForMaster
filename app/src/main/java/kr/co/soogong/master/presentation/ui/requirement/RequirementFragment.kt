package kr.co.soogong.master.presentation.ui.requirement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.RequirementViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.requirement.list.TAB_TEXTS_REQUIREMENTS_BEFORE_PROGRESS
import kr.co.soogong.master.presentation.ui.requirement.list.TAB_TEXTS_REQUIREMENTS_IN_PROGRESS
import kr.co.soogong.master.presentation.ui.requirement.list.filter.RequirementsFilterPagerAdapter
import kr.co.soogong.master.presentation.uihelper.requirment.RequirementsBadge
import kr.co.soogong.master.presentation.uihelper.requirment.SearchActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.changeTabFont
import kr.co.soogong.master.utility.extension.setTabSelectedListener
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class RequirementFragment : BaseFragment<FragmentRequirementBinding>(
    R.layout.fragment_requirement
), RequirementsBadge {

    private val mainViewModel: MainViewModel by activityViewModels()
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

            mainTabs.changeTabFont(0, R.style.title_3_bold)
            mainTabs.setTabSelectedListener(
                onSelected = { tab ->
                    tab?.position.let {
                        mainTabs.changeTabFont(it, R.style.title_3_bold)
                        viewModel.mainTabIndex.value = it
                    }
                },
                onUnselected = { tab ->
                    mainTabs.changeTabFont(tab?.position, R.style.title_3_regular)
                }
            )

            filterTabs.changeTabFont(0, R.style.subheadline_regular)
            filterTabs.setTabSelectedListener(
                onSelected = { tab ->
                    tab?.position.let {
                        filterTabs.changeTabFont(it, R.style.subheadline_bold)
                        viewModel.filterTabIndex.value = it
                    }
                },
                onUnselected = { tab ->
                    filterTabs.changeTabFont(tab?.position, R.style.subheadline_regular)
                }
            )

            setSearchIconClick { startActivity(SearchActivityHelper.getIntent(requireContext())) }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })

        viewModel.customerRequests.observe(viewLifecycleOwner) { request ->
            when {
                request.isEmpty -> Unit
                request.requestMeasureList.isNotEmpty() ->
                    showDialogForViewRequirement(REQUEST_MEASURE, request.requestMeasureList)
                request.requestConsultingList.isNotEmpty() ->
                    showDialogForViewRequirement(REQUEST_CONSULTING, request.requestConsultingList)
            }
        }

        viewModel.mainTabIndex.observe(viewLifecycleOwner) { mainTabIndex ->
            Timber.tag(TAG).d("setViewPager start: $mainTabIndex")
            with(binding) {
                requirementsViewPager.apply {
                    adapter = RequirementsFilterPagerAdapter(this@RequirementFragment, mainTabIndex)
                    TabLayoutMediator(filterTabs, this) { tab, position ->
                        tab.text =
                            if (mainTabIndex == 0) getString(TAB_TEXTS_REQUIREMENTS_BEFORE_PROGRESS[position])
                            else getString(TAB_TEXTS_REQUIREMENTS_IN_PROGRESS[position])
                    }.attach()
                }
            }
        }

        mainViewModel.selectedMainTabInRequirementFragment.observe(viewLifecycleOwner) { position ->
            binding.mainTabs.getTabAt(position)?.select()
        }
    }

    private fun showDialogForViewRequirement(type: Int, list: List<Int>) {
        DefaultDialog.newInstance(
            dialogData = if (type == REQUEST_MEASURE)
                DialogData.getNoticeForRequestMeasure(list.count())
            else
                DialogData.getNoticeForRequestConsulting(list.count())
        ).let {
            it.setButtonsClickListener(
                onPositive = {
                    startActivity(
                        ViewRequirementActivityHelper.getIntent(requireContext(), list.first())
                    )
                },
                onNegative = { }
            )
            it.show(parentFragmentManager, it.tag)
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.getCustomerRequests()
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
        tab?.badge?.badgeTextColor = resources.getColor(R.color.c_FFFFFF, null)
    }

    private fun invisibleBadge(tab: TabLayout.Tab?) {
        tab?.removeBadge()
    }

    companion object {
        private const val TAG = "RequirementsFragment"

        private const val REQUEST_MEASURE = 10
        private const val REQUEST_CONSULTING = 20

        fun newInstance() = RequirementFragment()
    }
}