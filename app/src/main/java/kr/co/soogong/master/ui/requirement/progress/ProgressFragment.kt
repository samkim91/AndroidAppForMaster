package kr.co.soogong.master.ui.requirement.progress

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementProgressBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getCallToCustomerDialogData
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.RequirementsBadge
import kr.co.soogong.master.uihelper.requirment.action.ActionViewHelper
import kr.co.soogong.master.uihelper.requirment.action.end.EndEstimateActivityHelper
import kr.co.soogong.master.utility.EventObserver
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

            progressList.adapter = ProgressAdapter(
                cardClickListener = { keycode, estimationStatus ->
                    startActivity(
                        ActionViewHelper.getIntent(
                            requireContext(),
                            keycode,
                            estimationStatus
                        )
                    )
                },
                callButtonClick = { keycode, number ->
                    val dialog = CustomDialog(getCallToCustomerDialogData(requireContext()),
                        yesClick = {
                            // Todo.. 전화한 기록을 더해야함.
                            viewModel.callToCustomer(estimationId = keycode, phoneNumber = number)
                            startActivity(CallToCustomerHelper.getIntent(number))
                        },
                        noClick = { }
                    )

                    dialog.show(childFragmentManager, dialog.tag)
                },
                doneButtonClick = { keycode ->
                    startActivity(EndEstimateActivityHelper.getIntent(requireContext(), keycode))
                }
            )

            val dividerItemDecoration = DividerItemDecoration(
                context,
                LinearLayoutManager(context).orientation
            )
            ResourcesCompat.getDrawable(resources, R.drawable.divider, null)?.let {
                dividerItemDecoration.setDrawable(it)
            }
            progressList.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
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
    }

    companion object {
        private const val TAG = "ProgressFragment"

        fun newInstance(): ProgressFragment {
            return ProgressFragment()
        }
    }
}