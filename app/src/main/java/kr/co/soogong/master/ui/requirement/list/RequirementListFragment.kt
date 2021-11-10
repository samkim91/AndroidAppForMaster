package kr.co.soogong.master.ui.requirement.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementListBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.requirement.RequirementViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.card.RequirementCardsAdapter
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class RequirementListFragment : BaseFragment<FragmentRequirementListBinding>(
    R.layout.fragment_requirement_list
) {
    private val viewModel: RequirementViewModel by viewModels({ requireParentFragment() })

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
                RequirementCardsAdapter(requireContext(), childFragmentManager, viewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.requestRequirements()
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "RequirementListFragment"

        fun newInstance() = RequirementListFragment()
    }
}