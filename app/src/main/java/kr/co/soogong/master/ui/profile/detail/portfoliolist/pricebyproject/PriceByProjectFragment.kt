package kr.co.soogong.master.ui.profile.detail.portfoliolist.pricebyproject

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditPriceByProjectBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.detail.portfoliolist.pricebyproject.PriceByProjectViewModel.Companion.GET_PRICE_BY_PROJECT_FAILED
import kr.co.soogong.master.ui.profile.detail.portfoliolist.pricebyproject.PriceByProjectViewModel.Companion.SAVE_PRICE_BY_PROJECT_FAILED
import kr.co.soogong.master.ui.profile.detail.portfoliolist.pricebyproject.PriceByProjectViewModel.Companion.SAVE_PRICE_BY_PROJECT_SUCCESSFULLY
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PORTFOLIO
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PRICE_BY_PROJECTS
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PRICE_BY_PROJECTS
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import kr.co.soogong.master.utility.validation.ValidationHelper
import timber.log.Timber

@AndroidEntryPoint
class PriceByProjectFragment : BaseFragment<FragmentEditPriceByProjectBinding>(
    R.layout.fragment_edit_price_by_project
) {
    private val pageName: String by lazy {
        arguments?.getString(PAGE_NAME) ?: ADD_PRICE_BY_PROJECTS
    }
    private val priceByProjectId: Int by lazy {
        arguments?.getInt(PRICE_BY_PROJECT_ID) ?: -1
    }

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

            when (pageName) {
                ADD_PRICE_BY_PROJECTS -> {
                    defaultButton.text = getString(R.string.writing_done)
                }
                EDIT_PRICE_BY_PROJECTS -> {
                    defaultButton.text = getString(R.string.modifying_done)
                    viewModel.requestPriceByProject(priceByProjectId)
                }
            }

            defaultButton.setOnClickListener {
                viewModel.title.observe(viewLifecycleOwner, {
                    projectTitle.alertVisible = it.isNullOrEmpty()
                })

                viewModel.price.observe(viewLifecycleOwner, {
                    projectPrice.alertVisible = it.isNullOrEmpty()
                })

                if (!projectTitle.alertVisible && !projectPrice.alertVisible && ValidationHelper.isIntRange(viewModel.price.value!!)) {
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
                SAVE_PRICE_BY_PROJECT_FAILED, GET_PRICE_BY_PROJECT_FAILED -> requireContext().toast(
                    getString(R.string.error_message_of_request_failed)
                )
            }
        })
    }

    companion object {
        private const val TAG = "EditPriceByProjectFragment"
        private const val PAGE_NAME = "PAGE_NAME"
        private const val PRICE_BY_PROJECT_ID = "PRICE_BY_PROJECT_ID"

        fun newInstance(pageName: String, priceByProjectId: Int?): PriceByProjectFragment {
            val fragment = PriceByProjectFragment()
            val args = Bundle()
            args.putString(PAGE_NAME, pageName)
            args.putInt(PRICE_BY_PROJECT_ID, priceByProjectId ?: -1)
            fragment.arguments = args
            return fragment
        }
    }
}