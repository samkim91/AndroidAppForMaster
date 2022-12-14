package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.portfolio

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.databinding.FragmentEditPortfolioBinding
import kr.co.soogong.master.domain.entity.profile.portfolio.Portfolio
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.DISMISS_LOADING
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.SHOW_LOADING
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.portfolio.PortfolioViewModel.Companion.SAVE_PORTFOLIO_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.portfolio.PortfolioViewModel.Companion.START_IMAGE_PICKER_FOR_AFTER
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.portfolio.PortfolioViewModel.Companion.START_IMAGE_PICKER_FOR_BEFORE
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class PortfolioFragment : BaseFragment<FragmentEditPortfolioBinding>(
    R.layout.fragment_edit_portfolio
) {
    private val viewModel: PortfolioViewModel by viewModels()

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

            saidBeforeRepairing.setImagesDeletableAdapter {
                viewModel.imageBeforeRepairing.removeAt(it)
            }

            saidAfterRepairing.setImagesDeletableAdapter { viewModel.imageAfterRepairing.removeAt(it) }

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                viewModel.title.observe(viewLifecycleOwner) {
                    stiTitle.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                }

                saidBeforeRepairing.error =
                    if (viewModel.imageBeforeRepairing.getItemCount() == 0) getString(R.string.required_field_alert) else null
                saidAfterRepairing.error =
                    if (viewModel.imageAfterRepairing.getItemCount() == 0) getString(R.string.required_field_alert) else null

                if (stiTitle.error.isNullOrEmpty() && saidBeforeRepairing.error.isNullOrEmpty() && saidAfterRepairing.error.isNullOrEmpty()) {
                    viewModel.savePortfolio()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_PORTFOLIO_SUCCESSFULLY -> activity?.finish()
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
                SHOW_LOADING -> showLoading(parentFragmentManager)
                DISMISS_LOADING -> dismissLoading()
                START_IMAGE_PICKER_FOR_BEFORE, START_IMAGE_PICKER_FOR_AFTER -> {
                    PermissionHelper.checkImagePermission(context = requireContext(),
                        onGranted = {
                            TedImagePicker.with(requireContext())
                                .buttonBackground(R.drawable.shape_green_background_radius8)
                                .start { uri ->
                                    if (FileHelper.isImageExtension(uri,
                                            requireContext()) == false
                                    ) {
                                        requireContext().toast(getString(R.string.invalid_image_extension))
                                        return@start
                                    }
                                    if (event == START_IMAGE_PICKER_FOR_BEFORE) {
                                        viewModel.imageBeforeRepairing.clear()
                                        viewModel.imageBeforeRepairing.add(
                                            AttachmentDto(
                                                id = null,
                                                partOf = null,
                                                referenceId = null,
                                                description = null,
                                                s3Name = null,
                                                fileName = null,
                                                url = null,
                                                uri = uri
                                            )
                                        )
                                    } else {
                                        viewModel.imageAfterRepairing.clear()
                                        viewModel.imageAfterRepairing.add(
                                            AttachmentDto(
                                                id = null,
                                                partOf = null,
                                                referenceId = null,
                                                description = null,
                                                s3Name = null,
                                                fileName = null,
                                                url = null,
                                                uri = uri
                                            )
                                        )
                                    }
                                }
                        },
                        onDenied = { })
                }
            }
        })
    }

    companion object {
        private val TAG = PortfolioFragment::class.java.simpleName
        private const val PORTFOLIO = "PORTFOLIO"

        fun newInstance(portfolio: Portfolio? = null) = PortfolioFragment().apply {
            portfolio?.let {
                arguments = bundleOf(
                    PORTFOLIO to it
                )
            }
        }

        fun getPortfolio(savedStateHandle: SavedStateHandle): MutableLiveData<Portfolio> =
            savedStateHandle.getLiveData(PORTFOLIO)
    }
}