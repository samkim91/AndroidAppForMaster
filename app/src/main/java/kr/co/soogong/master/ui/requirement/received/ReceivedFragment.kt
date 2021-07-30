package kr.co.soogong.master.ui.requirement.received

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.NotApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.databinding.FragmentRequirementReceivedBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.requirement.received.ReceivedViewModel.Companion.REQUEST_LIST_FAILED
import kr.co.soogong.master.uihelper.profile.EditRequiredInformationActivityHelper
import kr.co.soogong.master.uihelper.requirment.RequirementsBadge
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.EventObserver
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

            receivedList.adapter =
                ReceivedAdapter(cardClickListener = { requirementId ->
                    viewModel.masterApprovedStatus.value?.let {
                        if (it == NotApprovedCodeTable.code || it == RequestApproveCodeTable.code) {
                            val dialog =
                                CustomDialog(
                                    DialogData.getAskingFillProfileDialogData(requireContext()),
                                    yesClick = {
                                        startActivity(
                                            EditRequiredInformationActivityHelper.getIntent(
                                                requireContext()
                                            )
                                        )
                                    },
                                    noClick = { })
                            dialog.show(parentFragmentManager, dialog.tag)
                        } else {
                            startActivity(
                                ViewRequirementActivityHelper.getIntent(
                                    requireContext(),
                                    requirementId,
                                )
                            )
                        }
                    }
                })

            val dividerItemDecoration = DividerItemDecoration(
                context,
                LinearLayoutManager(context).orientation
            )
            ResourcesCompat.getDrawable(resources, R.drawable.divider, null)?.let {
                dividerItemDecoration.setDrawable(it)
            }
            receivedList.addItemDecoration(dividerItemDecoration)
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
                REQUEST_LIST_FAILED -> {
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