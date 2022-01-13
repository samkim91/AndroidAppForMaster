package kr.co.soogong.master.ui.requirement.list.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.databinding.FragmentRequirementListBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.main.MainViewModel
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.requirement.card.RequirementCardsAdapter
import kr.co.soogong.master.ui.requirement.list.RequirementsViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class RequirementsFilterFragment : BaseFragment<FragmentRequirementListBinding>(
    R.layout.fragment_requirement_list
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    private val parentViewModel: RequirementViewModel by viewModels({ requireParentFragment() })
    private val viewModel: RequirementsFilterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            recyclerViewRequirements.adapter =
                RequirementCardsAdapter(requireContext(),
                    childFragmentManager,
                    activityViewModel,
                    viewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")

        viewModel.requirementStatus.value = RequirementStatus.getRequirementStatusFromTabIndex(
            parentViewModel.mainTabIndex.value,
            parentViewModel.filterTabIndex.value)

        viewModel.initList()
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        parentViewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "RequirementsFilterFragment"

        fun newInstance() = RequirementsFilterFragment()
    }
}