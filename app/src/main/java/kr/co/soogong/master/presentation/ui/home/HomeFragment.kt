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
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel.Companion.REQUEST_FAILED
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

            fciMeasuring.setOnClickListener {
                // 문의목록 -> 방문예정
                mainViewModel.selectedMainTabInMainActivity.value = 1
                mainViewModel.selectedFilterTabInRequirementFragment.value = 2
            }

            fciRepairing.setOnClickListener {
                // 문의목록 -> 시공예정
                mainViewModel.selectedMainTabInMainActivity.value = 1
                mainViewModel.selectedFilterTabInRequirementFragment.value = 3
            }

            fciDone.setOnClickListener {
                // 문의목록 -> 완료
                mainViewModel.selectedMainTabInMainActivity.value = 1
                mainViewModel.selectedFilterTabInRequirementFragment.value = 4
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
        private val TAG = HomeFragment::class.java.simpleName

        fun newInstance() = HomeFragment()
    }
}