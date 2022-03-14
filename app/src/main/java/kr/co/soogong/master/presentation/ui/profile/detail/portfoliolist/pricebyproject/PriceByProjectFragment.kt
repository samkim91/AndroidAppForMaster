package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.pricebyproject

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import kr.co.soogong.master.databinding.FragmentEditPriceByProjectBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.pricebyproject.PriceByProjectViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.pricebyproject.PriceByProjectViewModel.Companion.SAVE_PRICE_BY_PROJECT_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.isIntRange
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class PriceByProjectFragment : BaseFragment<FragmentEditPriceByProjectBinding>(
    R.layout.fragment_edit_price_by_project
) {
    private val viewModel: PriceByProjectViewModel by viewModels()

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

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                viewModel.title.observe(viewLifecycleOwner) {
                    stiTitle.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                }

                viewModel.price.value.let {
                    stiPrice.error = when {
                        it == null || it < 10000L -> getString(R.string.minimum_cost)
                        !it.isIntRange() -> getString(R.string.too_large_number)
                        else -> null
                    }
                }

                viewModel.description.observe(viewLifecycleOwner) {
                    stcDescription.error =
                        if (it.length < 10) getString(R.string.fill_text_over_10) else null
                }

                if (stiTitle.error.isNullOrEmpty() && stiPrice.error.isNullOrEmpty() && stcDescription.error.isNullOrEmpty()) {
                    viewModel.savePriceByProject()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_PRICE_BY_PROJECT_SUCCESSFULLY -> activity?.finish()
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "EditPriceByProjectFragment"
        private const val PRICE_BY_PROJECT = "PRICE_BY_PROJECT"

        fun newInstance(priceByProject: PortfolioDto? = null) = PriceByProjectFragment().apply {
            priceByProject?.let {
                arguments = bundleOf(
                    PRICE_BY_PROJECT to it
                )
            }
        }

        fun getPriceByProject(savedStateHandle: SavedStateHandle): MutableLiveData<PortfolioDto> =
            savedStateHandle.getLiveData(PRICE_BY_PROJECT)
    }
}