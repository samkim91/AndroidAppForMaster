package kr.co.soogong.master.presentation.ui.requirement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.RequirementViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.requirement.list.filter.FILTER_TEXTS_IN_REQUIREMENT_FRAGMENT
import kr.co.soogong.master.presentation.ui.requirement.list.filter.RequirementsFilterPagerAdapter
import kr.co.soogong.master.presentation.uihelper.requirment.SearchActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class RequirementFragment : BaseFragment<FragmentRequirementBinding>(
    R.layout.fragment_requirement
) {

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

            requirementsViewPager.apply {
                offscreenPageLimit = 1
                adapter = RequirementsFilterPagerAdapter(this@RequirementFragment)
                TabLayoutMediator(filterTabs, this) { tab, position ->
                    tab.text = getString(FILTER_TEXTS_IN_REQUIREMENT_FRAGMENT[position].first)
                }.attach()
            }

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

        mainViewModel.selectedFilterTabInRequirementFragment.observe(viewLifecycleOwner) { position ->
            binding.filterTabs.getTabAt(position)?.select()
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

    companion object {
        private val TAG = RequirementFragment::class.java.simpleName

        private const val REQUEST_MEASURE = 10
        private const val REQUEST_CONSULTING = 20

        fun newInstance() = RequirementFragment()
    }
}