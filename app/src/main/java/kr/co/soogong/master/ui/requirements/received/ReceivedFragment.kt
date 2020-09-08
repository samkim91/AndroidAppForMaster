package kr.co.soogong.master.ui.requirements.received

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsReceivedBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.ui.requirements.RequirementsBadge
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

class ReceivedFragment : BaseFragment<FragmentRequirementsReceivedBinding>(
    R.layout.fragment_requirements_received
) {
    private val viewModel: ReceivedViewModel by viewModels {
        ReceivedViewModelFactory(getRepository(requireContext()))
    }

    private var requirementsBadge: RequirementsBadge? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag(TAG).d("onViewCreated: ")
        bind {
            receivedList.adapter = ReceivedAdapter()
            val dividerItemDecoration = DividerItemDecoration(
                context,
                LinearLayoutManager(context).orientation
            )
            receivedList.addItemDecoration(dividerItemDecoration)
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@ReceivedFragment.viewLifecycleOwner
        }

        binding.testButton.setOnClickListener {
            viewModel.requestList()
        }

        requirementsBadge = parentFragment as? RequirementsBadge

        registerEventObserve()
    }

    private fun registerEventObserve() {
        viewModel.event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
            when (event) {
                ReceivedViewModel.BADGE_UPDATE -> {
                    if (value > 0) requirementsBadge?.setReceivedBadge(value) else requirementsBadge?.unsetReceivedBadge()
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