package kr.co.soogong.master.ui.requirement.done

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementDoneBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.requirement.card.RequirementCardsAdapter
import kr.co.soogong.master.ui.requirement.RequirementChipGroupHelper
import kr.co.soogong.master.ui.requirement.RequirementViewModel.Companion.ASK_FOR_REVIEW_FAILED
import kr.co.soogong.master.ui.requirement.RequirementViewModel.Companion.ASK_FOR_REVIEW_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.done.DoneViewModel.Companion.BADGE_UPDATE
import kr.co.soogong.master.ui.requirement.doneStatus
import kr.co.soogong.master.uihelper.requirment.RequirementsBadge
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.onCheckedChanged
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class DoneFragment : BaseFragment<FragmentRequirementDoneBinding>(
    R.layout.fragment_requirement_done
) {
    private val viewModel: DoneViewModel by viewModels()

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

            RequirementChipGroupHelper(layoutInflater, filterGroup, doneStatus)
            filterGroup.onCheckedChanged { index -> viewModel.onFilterChange(index) }

            doneList.adapter = RequirementCardsAdapter(requireContext(), childFragmentManager, viewModel)
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
                BADGE_UPDATE -> {
                    (parentFragment as? RequirementsBadge)?.setDoneBadge(value as Int)
                }
                ASK_FOR_REVIEW_SUCCESSFULLY -> {
                    requireContext().toast(getString(R.string.ask_for_review_successful))
                }
                ASK_FOR_REVIEW_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "DoneFragment"

        fun newInstance(): DoneFragment {
            return DoneFragment()
        }
    }
}