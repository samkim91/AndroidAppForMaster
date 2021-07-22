package kr.co.soogong.master.ui.profile.detail.flexiblecost

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditFlexibleCostBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.detail.flexiblecost.EditFlexibleCostViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.profile.detail.flexiblecost.EditFlexibleCostViewModel.Companion.SAVE_FLEXIBLE_COST_SUCCESSFULLY
import kr.co.soogong.master.ui.profile.detail.flexiblecost.FlexibleCostChipGroupHelper.CRANE_USAGE
import kr.co.soogong.master.ui.profile.detail.flexiblecost.FlexibleCostChipGroupHelper.PACKAGE_COST
import kr.co.soogong.master.ui.profile.detail.flexiblecost.FlexibleCostChipGroupHelper.TRAVEL_COST
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditFlexibleCostFragment : BaseFragment<FragmentEditFlexibleCostBinding>(
    R.layout.fragment_edit_flexible_cost
) {
    private val viewModel: EditFlexibleCostViewModel by viewModels()

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

            initChipsGroup()
            defaultButton.setOnClickListener {
                viewModel.saveFlexibleCosts()
            }
        }
    }

    private fun initChipsGroup() {
        bind {
            FlexibleCostChipGroupHelper(layoutInflater, travelCostChipGroup, TRAVEL_COST)
            FlexibleCostChipGroupHelper(layoutInflater, craneUsageChipGroup, CRANE_USAGE)
            FlexibleCostChipGroupHelper(layoutInflater, packageCostChipGroup, PACKAGE_COST)
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when(event) {
                SAVE_FLEXIBLE_COST_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestFlexibleCosts()
    }

    companion object {
        private const val TAG = "EditFlexibleCostFragment"

        fun newInstance() = EditFlexibleCostFragment()
    }
}