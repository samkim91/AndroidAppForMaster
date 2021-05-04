package kr.co.soogong.master.ui.requirements.received

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsReceivedBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.uiinterface.requirments.RequirementsBadge
import kr.co.soogong.master.uiinterface.requirments.action.ActionViewHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class ReceivedFragment : BaseFragment<FragmentRequirementsReceivedBinding>(
    R.layout.fragment_requirements_received
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
                ReceivedAdapter(cardClickListener = { keycode, estimationStatus ->
                    viewModel.isApprovedMaster.value?.let {
                        if (it) {
                            startActivity(
                                ActionViewHelper.getIntent(
                                    requireContext(),
                                    keycode,
                                    estimationStatus
                                )
                            )
                        } else {
                            val dialog =
                                CustomDialog(DialogData.askingFillProfileDialogData(requireContext()),
                                    yesClick = {
                                        //Todo.. 필수정보 등록 activity로 이동
                                    },
                                    noClick = { })
                        dialog.show(parentFragmentManager, dialog.tag)
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
                (parentFragment as? RequirementsBadge)?.setReceivedBadge(value)
            }
        }
    })
    viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
        when (action) {
            ReceivedViewModel.BADGE_UPDATE -> {
                binding.receivedList.scrollToPosition(0)
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