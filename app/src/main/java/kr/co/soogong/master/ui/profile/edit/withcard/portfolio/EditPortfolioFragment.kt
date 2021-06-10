package kr.co.soogong.master.ui.profile.edit.withcard.portfolio

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditPortfolioBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.withcard.portfolio.EditPortfolioViewModel.Companion.GET_PORTFOLIO_FAILED
import kr.co.soogong.master.ui.profile.edit.withcard.portfolio.EditPortfolioViewModel.Companion.SAVE_PORTFOLIO_FAILED
import kr.co.soogong.master.ui.profile.edit.withcard.portfolio.EditPortfolioViewModel.Companion.SAVE_PORTFOLIO_SUCCESSFULLY
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.ADD_PORTFOLIO
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_PORTFOLIO
import kr.co.soogong.master.ui.utils.PermissionHelper
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditPortfolioFragment : BaseFragment<FragmentEditPortfolioBinding>(
    R.layout.fragment_edit_portfolio
) {
    private val pageName: String by lazy {
        arguments?.getString(PAGE_NAME) ?: ADD_PORTFOLIO
    }
    private val portfolioId: Int by lazy {
        arguments?.getInt(PORTFOLIO_ID) ?: -1
    }

    private val viewModel: EditPortfolioViewModel by viewModels()

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
                    with(defaultButton) {
                        text = getString(R.string.writing_done)
                        setOnClickListener {
                            viewModel.savePortfolio(-1)
                        }
                    }
                }
                EDIT_PORTFOLIO -> {
                    viewModel.requestPortfolio(portfolioId)
                    with(defaultButton) {
                        text = getString(R.string.modifying_done)
                        setOnClickListener {
                            viewModel.savePortfolio(portfolioId)
                        }
                    }
                }
            }

            cameraIconBeforeJob.setOnClickListener {
                PermissionHelper.checkImagePermission(context = requireContext(),
                    onGranted = {
                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_fill_green_background)
                            .start { uri -> viewModel.imageBeforeJob.value = uri }
                    },
                    onDenied = {
                        requireContext().toast(getString(R.string.permission_denied_message))
                    })
            }

            cameraIconAfterJob.setOnClickListener {
                PermissionHelper.checkImagePermission(context = requireContext(),
                    onGranted = {
                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_fill_green_background)
                            .start { uri -> viewModel.imageAfterJob.value = uri }
                    },
                    onDenied = {
                        requireContext().toast(getString(R.string.permission_denied_message))
                    })
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
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

        fun newInstance(pageName: String, portfolioId: Int?): EditPortfolioFragment {
            val fragment = EditPortfolioFragment()
            val args = Bundle()
            args.putString(PAGE_NAME, pageName)
            args.putInt(PORTFOLIO_ID, portfolioId ?: -1)
            fragment.arguments = args
            return fragment
        }
    }
}