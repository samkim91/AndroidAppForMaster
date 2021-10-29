package kr.co.soogong.master.ui.requirement.received

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementStatus.Companion.receivedStatus
import kr.co.soogong.master.databinding.FragmentRequirementReceivedBinding
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
class ReceivedFragment : BaseFragment<FragmentRequirementReceivedBinding>(
    R.layout.fragment_requirement_received
) {
    private val viewModel: ReceivedViewModel by viewModels()

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

            RequirementChipGroupHelper(layoutInflater, filterGroup, receivedStatus)
            filterGroup.onCheckedChanged { index -> viewModel.onFilterChange(index) }

            receivedList.adapter =
                RequirementCardsAdapter(requireContext(), childFragmentManager, viewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.requestList()
        viewModel.requestMasterSimpleInfo()
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
            when (event) {
                ReceivedViewModel.BADGE_UPDATE -> {
                    (parentFragment as? RequirementsBadge)?.setReceivedBadge(value as Int)
                }
            }
        })

        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                ReceivedViewModel.BADGE_UPDATE -> {
                    binding.receivedList.scrollToPosition(0)
                }
                REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "ReceivedFragment"

        fun newInstance(): ReceivedFragment {
            return ReceivedFragment()
        }
    }
}