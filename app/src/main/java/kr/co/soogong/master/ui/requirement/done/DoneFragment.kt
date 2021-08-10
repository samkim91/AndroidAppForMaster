package kr.co.soogong.master.ui.requirement.done

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.FragmentRequirementDoneBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.requirement.done.DoneViewModel.Companion.ASK_FOR_REVIEW_FAILED
import kr.co.soogong.master.ui.requirement.done.DoneViewModel.Companion.ASK_FOR_REVIEW_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.done.DoneViewModel.Companion.BADGE_UPDATE
import kr.co.soogong.master.uihelper.requirment.RequirementsBadge
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.EventObserver
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

            swipeRefreshLayout.setColorSchemeResources(R.color.app_color)
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.requestList()
                swipeRefreshLayout.isRefreshing = false
            }

            doneList.adapter = DoneAdapter(
                cardClickListener = { requirementId ->
                    startActivity(
                        ViewRequirementActivityHelper.getIntent(
                            requireContext(),
                            requirementId,
                        )
                    )
                },
                reviewButtonClick = { _, requirementCard ->
                    viewModel.askForReview(requirementCard = (requirementCard as? RequirementCard))
                }
            )

            val dividerItemDecoration = DividerItemDecoration(
                context,
                LinearLayoutManager(context).orientation
            )
            ResourcesCompat.getDrawable(resources, R.drawable.divider, null)?.let {
                dividerItemDecoration.setDrawable(it)
            }
            doneList.addItemDecoration(dividerItemDecoration)
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