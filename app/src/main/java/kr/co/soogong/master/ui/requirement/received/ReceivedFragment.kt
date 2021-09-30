package kr.co.soogong.master.ui.requirement.received

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.CompareCodeTable
import kr.co.soogong.master.data.model.profile.NotApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.FragmentRequirementReceivedBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.requirement.RequirementChipGroupHelper
import kr.co.soogong.master.ui.requirement.received.ReceivedViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.receivedStatus
import kr.co.soogong.master.uihelper.profile.EditRequiredInformationActivityHelper
import kr.co.soogong.master.uihelper.requirment.RequirementsBadge
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.MeasureActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
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
                ReceivedAdapter(cardClickListener = { requirementId ->
                    viewModel.masterSimpleInfo.value?.approvedStatus.let {
                        when (it) {
                            // 미승인 상태이면, 필수정보를 채우도록 이동
                            NotApprovedCodeTable.code -> {
                                val dialog =
                                    CustomDialog.newInstance(
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
                            }
                            // 승인요청 상태이면, 승인될 때까지 기다리라는 문구
                            RequestApproveCodeTable.code -> {
                                val dialog =
                                    CustomDialog.newInstance(
                                        DialogData.getWaitingUntilApprovalDialogData(
                                            requireContext()
                                        ),
                                        yesClick = { },
                                        noClick = { })
                                dialog.show(parentFragmentManager, dialog.tag)
                            }
                            // 승인 상태이면, 문의 세부정보로 이동
                            else -> {
                                startActivity(
                                    ViewRequirementActivityHelper.getIntent(
                                        requireContext(),
                                        requirementId,
                                    )
                                )
                            }
                        }
                    }
                },
                    leftButtonClick = { _, _ -> },
                    rightButtonClick = { requirementId, _ ->
                        startActivity(
                            EndRepairActivityHelper.getIntent(
                                requireContext(),
                                requirementId
                            )
                        )
                    })
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.requestMasterSimpleInfo()
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