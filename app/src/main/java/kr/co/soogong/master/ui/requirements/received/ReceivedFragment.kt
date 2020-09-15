package kr.co.soogong.master.ui.requirements.received

import android.content.Intent
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
import kr.co.soogong.master.ui.requirements.received.detail.ReceivedDetailActivity
import kr.co.soogong.master.uiinterface.requirments.received.detail.ReceivedDetailActivityHelper
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
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()

        registerEventObserve()
    }

    override fun initLayout() {
        bind {
            receivedList.adapter = ReceivedAdapter().apply {
                buttonClick = { keycode ->
                    context.run {
                        startActivity(Intent(this, ReceivedDetailActivity::class.java).apply {
                            putExtra(ReceivedDetailActivityHelper.EXTRA_KEY_BUNDLE, Bundle().apply {
                                putString(
                                    ReceivedDetailActivityHelper.BUNDLE_KEY_RECEIVED_KEY,
                                    keycode
                                )
                            })
                        })
                    }
                }
            }

            val dividerItemDecoration = DividerItemDecoration(
                context,
                LinearLayoutManager(context).orientation
            )
            receivedList.addItemDecoration(dividerItemDecoration)
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }

        requirementsBadge = parentFragment as? RequirementsBadge
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