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
import kr.co.soogong.master.ui.requirement.progress.ProgressViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.RequirementsBadge
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.PermissionHelper
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

            progressList.adapter = ProgressAdapter(
                cardClickListener = { requirementId ->
                    startActivity(
                        ViewRequirementActivityHelper.getIntent(
                            requireContext(),
                            requirementId,
                        )
                    )
                },
                callButtonClick = { keycode, number ->
                    PermissionHelper.checkCallPermission(
                        context = requireContext(),
                        onGranted = {
                            val dialog = CustomDialog(getCallToCustomerDialogData(requireContext()),
                                yesClick = {
                                    viewModel.callToClient(
                                        requirementId = keycode
                                    )
                                    startActivity(CallToCustomerHelper.getIntent(number.toString()))
                                },
                                noClick = { }
                            )

                            dialog.show(childFragmentManager, dialog.tag)
                        },
                        onDenied = { }
                    )
                },
                doneButtonClick = { requirementId, _ ->
                    startActivity(
                        EndRepairActivityHelper.getIntent(
                            requireContext(),
                            requirementId
                        )
                    )
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