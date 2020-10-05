package kr.co.soogong.master.ui.requirements.progress

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsProgressBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.ui.requirements.RequirementsBadge
import kr.co.soogong.master.uiinterface.requirments.progress.detail.ProgressDetailActivityHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

class ProgressFragment : BaseFragment<FragmentRequirementsProgressBinding>(
    R.layout.fragment_requirements_progress
) {
    private val viewModel: ProgressViewModel by viewModels {
        ProgressViewModelFactory(getRepository(requireContext()))
    }

    private var requirementsBadge: RequirementsBadge? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()

        requirementsBadge = parentFragment as? RequirementsBadge

        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner

            receivedList.adapter = ProgressAdapter().apply {
                detailButtonClick = { keycode ->
                    startActivity(ProgressDetailActivityHelper.getIntent(requireContext(), keycode))
                }

                callButtonClick = { number ->
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
                    startActivity(intent)
                }

                removeButtonClick = { keycode ->
                    Timber.tag(TAG).i("onViewCreated: $keycode")
                }
            }

            val dividerItemDecoration = DividerItemDecoration(
                context,
                LinearLayoutManager(context).orientation
            )
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
                ProgressViewModel.BADGE_UPDATE -> {
                    if (value > 0) requirementsBadge?.setProgressBadge(value) else requirementsBadge?.unsetProgressBadge()
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