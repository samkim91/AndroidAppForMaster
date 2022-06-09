package kr.co.soogong.master.presentation.ui.requirement.list.filter

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementListBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.RequirementViewModel
import kr.co.soogong.master.presentation.ui.requirement.card.RequirementCardsAdapter
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class RequirementsFilterFragment : BaseFragment<FragmentRequirementListBinding>(
    R.layout.fragment_requirement_list
) {

    private val mainViewModel: MainViewModel by activityViewModels()
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
                    mainViewModel,
                    viewModel)
        }
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

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.initList()
    }

    companion object {
        private val TAG = RequirementsFilterFragment::class.java.simpleName
        private const val REQUIREMENT_STATUS = "REQUIREMENT_STATUS"

        fun newInstance(status: List<String>) = RequirementsFilterFragment().apply {
            arguments = bundleOf(
                REQUIREMENT_STATUS to status
            )
        }

        fun getRequirementStatusFromSavedState(savedStateHandle: SavedStateHandle) =
            savedStateHandle.getLiveData<List<String>>(REQUIREMENT_STATUS)
    }
}