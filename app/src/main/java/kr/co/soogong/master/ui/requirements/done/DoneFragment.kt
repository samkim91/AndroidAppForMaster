package kr.co.soogong.master.ui.requirements.done

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsDoneBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uiinterface.requirments.RequirementsBadge
import kr.co.soogong.master.uiinterface.requirments.action.ActionViewHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class DoneFragment : BaseFragment<FragmentRequirementsDoneBinding>(
    R.layout.fragment_requirements_done
) {
    private val viewModel: DoneViewModel by viewModels()

    private var requirementsBadge: RequirementsBadge? = null

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

            doneList.adapter = DoneAdapter(cardClickListener = { keycode, estimationStatus ->
                startActivity(
                    ActionViewHelper.getIntent(
                        requireContext(),
                        keycode,
                        estimationStatus
                    )
                )
            })

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

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestList()
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
            when (event) {
                DoneViewModel.BADGE_UPDATE -> {
                    Timber.tag(TAG).d("registerEventObserve: $value")
                    Timber.tag(TAG).d("registerEventObserve: $requirementsBadge")
                    if (value > 0) requirementsBadge?.setDoneBadge(value) else requirementsBadge?.unsetDoneBadge()
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