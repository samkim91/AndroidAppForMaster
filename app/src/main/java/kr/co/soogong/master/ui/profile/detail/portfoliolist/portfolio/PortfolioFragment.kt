package kr.co.soogong.master.ui.profile.detail.portfoliolist.portfolio

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditPortfolioBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.detail.portfoliolist.portfolio.PortfolioViewModel.Companion.GET_PORTFOLIO_FAILED
import kr.co.soogong.master.ui.profile.detail.portfoliolist.portfolio.PortfolioViewModel.Companion.SAVE_PORTFOLIO_FAILED
import kr.co.soogong.master.ui.profile.detail.portfoliolist.portfolio.PortfolioViewModel.Companion.SAVE_PORTFOLIO_SUCCESSFULLY
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PORTFOLIO
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PORTFOLIO
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class PortfolioFragment : BaseFragment<FragmentEditPortfolioBinding>(
    R.layout.fragment_edit_portfolio
) {
    private val pageName: String by lazy {
        arguments?.getString(PAGE_NAME) ?: ADD_PORTFOLIO
    }
    private val portfolioId: Int by lazy {
        arguments?.getInt(PORTFOLIO_ID) ?: -1
    }

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

            when (pageName) {
                ADD_PORTFOLIO -> {
                    defaultButton.text = getString(R.string.writing_done)
                }
                EDIT_PORTFOLIO -> {
                    defaultButton.text = getString(R.string.modifying_done)
                    viewModel.requestPortfolio(portfolioId)
                }
            }

            defaultButton.setOnClickListener {
                viewModel.title.observe(viewLifecycleOwner, {
                    jobTitle.alertVisible = it.isNullOrEmpty()
                })

                alert.isVisible =
                    viewModel.imageBeforeJob.value?.equals(Uri.EMPTY)!! || viewModel.imageAfterJob.value?.equals(
                        Uri.EMPTY
                    )!!

                viewModel.description.observe(viewLifecycleOwner, {
                    jobDescription.alertVisible = it.length < 10
                })

                if (!jobTitle.alertVisible && !alert.isVisible && !jobDescription.alertVisible) {
                    showLoading(parentFragmentManager)
                    viewModel.savePortfolio()
                }
            }

            cameraIconBeforeJob.setOnClickListener {
                PermissionHelper.checkImagePermission(context = requireContext(),
                    onGranted = {
                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_green_background_radius8)
                            .start { uri ->
                                if (FileHelper.isImageExtension(uri, requireContext()) == false) {
                                    requireContext().toast(getString(R.string.invalid_image_extension))
                                    return@start
                                }

                                viewModel.imageBeforeJob.value = uri
                            }
                    },
                    onDenied = { })
            }

            cameraIconAfterJob.setOnClickListener {
                PermissionHelper.checkImagePermission(context = requireContext(),
                    onGranted = {
                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_green_background_radius8)
                            .start { uri ->
                                if (FileHelper.isImageExtension(uri, requireContext()) == false) {
                                    requireContext().toast(getString(R.string.invalid_image_extension))
                                    return@start
                                }

                                viewModel.imageAfterJob.value = uri
                            }
                    },
                    onDenied = { })
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            dismissLoading()
            when (event) {
                SAVE_PORTFOLIO_SUCCESSFULLY -> activity?.onBackPressed()
                SAVE_PORTFOLIO_FAILED, GET_PORTFOLIO_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "EditPortfolioFragment"
        private const val PAGE_NAME = "PAGE_NAME"
        private const val PORTFOLIO_ID = "PORTFOLIO_ID"

        fun newInstance(pageName: String, portfolioId: Int?): PortfolioFragment {
            val fragment = PortfolioFragment()
            val args = Bundle()
            args.putString(PAGE_NAME, pageName)
            args.putInt(PORTFOLIO_ID, portfolioId ?: -1)
            fragment.arguments = args
            return fragment
        }
    }
}