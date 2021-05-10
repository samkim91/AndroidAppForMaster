package kr.co.soogong.master.ui.profile.edit.flexiblecost

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditFlexibleCostBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.flexiblecost.FlexibleCostChipGroupHelper.CRANE_USAGE
import kr.co.soogong.master.ui.profile.edit.flexiblecost.FlexibleCostChipGroupHelper.PACKAGE_COST
import kr.co.soogong.master.ui.profile.edit.flexiblecost.FlexibleCostChipGroupHelper.TRAVEL_COST
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_FLEXIBLE_COST
import kr.co.soogong.master.util.extension.onClickListener
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

            initChips()
            defaultButton.setOnClickListener {
                viewModel.saveFlexibleCosts()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    private fun initChips() {
        bind {
            FlexibleCostChipGroupHelper(layoutInflater, travelCostChipGroup, TRAVEL_COST)
            FlexibleCostChipGroupHelper(layoutInflater, craneUsageChipGroup, CRANE_USAGE)
            FlexibleCostChipGroupHelper(layoutInflater, packageCostChipGroup, PACKAGE_COST)
        }
        viewModel.getFlexibleCosts()
    }

    companion object {
        private const val TAG = "EditFlexibleCostFragment"
        private const val PAGE_NAME = "PAGE_NAME"

        fun newInstance() = EditFlexibleCostFragment()
    }
}