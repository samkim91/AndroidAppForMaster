package kr.co.soogong.master.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentHomeBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.home.HomeViewModel.Companion.UPDATE_REQUEST_MEASURE_YN_SUCCESSFUL
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.main.TAB_TEXTS_MAIN_NAVIGATION
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.uihelper.requirment.DoneActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            mainVm = mainViewModel
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            newRequirementsRecyclerView.adapter =
                SimpleRequirementCardAdapter(requireContext(),
                    childFragmentManager,
                    mainViewModel)

            fciBeforeProgress.setOnClickListener {
                // 문의 목록 -> 진행 전 으로 이동
                mainViewModel.selectedMainTabInMainActivity.value =
                    TAB_TEXTS_MAIN_NAVIGATION.indexOf(R.string.main_activity_navigation_bar_requirements)
                mainViewModel.selectedMainTabInRequirementFragment.value = 0
            }

            fciProcessing.setOnClickListener {
                // 문의 목록 -> 진행 중 으로 이동
                mainViewModel.selectedMainTabInMainActivity.value =
                    TAB_TEXTS_MAIN_NAVIGATION.indexOf(R.string.main_activity_navigation_bar_requirements)
                mainViewModel.selectedMainTabInRequirementFragment.value = 1
            }

            fciAfterProcess.setOnClickListener {
                startActivity(DoneActivityHelper.getIntent(requireContext()))
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        with(viewModel) {
            action.observe(viewLifecycleOwner, EventObserver { event ->
                when (event) {
                    REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
                    UPDATE_REQUEST_MEASURE_YN_SUCCESSFUL -> mainViewModel.requestMasterSettings()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.requestRequirementTotal()
        viewModel.initList()
    }

    companion object {
        private const val TAG = "HomeFragment"

        fun newInstance() = HomeFragment()
    }
}