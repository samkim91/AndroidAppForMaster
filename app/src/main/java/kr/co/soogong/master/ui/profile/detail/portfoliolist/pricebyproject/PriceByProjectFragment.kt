package kr.co.soogong.master.ui.profile.detail.portfoliolist.pricebyproject

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditPriceByProjectBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.ui.profile.detail.portfoliolist.pricebyproject.PriceByProjectViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.profile.detail.portfoliolist.pricebyproject.PriceByProjectViewModel.Companion.SAVE_PRICE_BY_PROJECT_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import kr.co.soogong.master.utility.validation.ValidationHelper
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
                viewModel.title.observe(viewLifecycleOwner, {
                    stiTitle.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                })

                viewModel.price.observe(viewLifecycleOwner, {
                    stiPrice.error = when {
                        it.isNullOrEmpty() -> getString(R.string.required_field_alert)
                        !ValidationHelper.isIntRange(it) -> getString(R.string.too_large_number)
                        else -> null
                    }
                })

                viewModel.description.observe(viewLifecycleOwner, {
                    stcDescription.error =
                        if (it.length < 10) getString(R.string.fill_text_over_10) else null
                })

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
                SAVE_PRICE_BY_PROJECT_SUCCESSFULLY -> activity?.onBackPressed()
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "EditPriceByProjectFragment"
        private const val PRICE_BY_PROJECT_ID = "PRICE_BY_PROJECT_ID"

        fun newInstance(priceByProjectId: Int? = null) = PriceByProjectFragment().apply {
            arguments = Bundle().apply {
                priceByProjectId?.let { putInt(PRICE_BY_PROJECT_ID, it) }
            }
        }

        fun getPriceByProjectId(savedStateHandle: SavedStateHandle): Int? =
            savedStateHandle.get(PRICE_BY_PROJECT_ID)
    }
}