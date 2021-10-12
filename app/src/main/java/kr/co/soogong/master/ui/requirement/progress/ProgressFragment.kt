package kr.co.soogong.master.ui.requirement.progress

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementStatus.Companion.progressStatus
import kr.co.soogong.master.databinding.FragmentRequirementProgressBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.requirement.RequirementChipGroupHelper
import kr.co.soogong.master.ui.requirement.RequirementViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.card.RequirementCardsAdapter
import kr.co.soogong.master.uihelper.requirment.RequirementsBadge
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.onCheckedChanged
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class ProgressFragment : BaseFragment<FragmentRequirementProgressBinding>(
    R.layout.fragment_requirement_progress
) {
    private val viewModel: ProgressViewModel by viewModels()

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

            RequirementChipGroupHelper(layoutInflater, filterGroup, progressStatus)
            filterGroup.onCheckedChanged { index -> viewModel.onFilterChange(index) }

            progressList.adapter =
                RequirementCardsAdapter(requireContext(), childFragmentManager, viewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.requestList()
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
            when (event) {
                ProgressViewModel.BADGE_UPDATE -> {
                    (parentFragment as? RequirementsBadge)?.setProgressBadge(value as Int)
                }
            }
        })

        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "ProgressFragment"

        fun newInstance(): ProgressFragment {
            return ProgressFragment()
        }
    }
}