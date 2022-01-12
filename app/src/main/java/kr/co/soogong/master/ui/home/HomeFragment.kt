package kr.co.soogong.master.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentHomeBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.main.MainViewModel
import kr.co.soogong.master.ui.requirement.RequirementViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.RequirementViewModel.Companion.SET_CURRENT_TAB
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {
    private val activityViewModel: MainViewModel by activityViewModels()
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
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            switchCompat.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateRequestMeasureYn(isChecked)
            }

            newRequirementsRecyclerView.adapter =
                SimpleRequirementCardAdapter(requireContext(), childFragmentManager, viewModel)

            fciBeforeProgress.setOnClickListener {
                // 문의 목록 -> 진행 전 으로 이동
                activityViewModel.selectedMainTabInMainActivity.value = 1
                activityViewModel.selectedMainTabInRequirementFragment.value = 0
            }

            fciProcessing.setOnClickListener {
                // 문의 목록 -> 진행 중 으로 이동
                activityViewModel.selectedMainTabInMainActivity.value = 1
                activityViewModel.selectedMainTabInRequirementFragment.value = 1
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        with(viewModel) {
            action.observe(viewLifecycleOwner, EventObserver { event ->
                when (event) {
                    REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            })
            event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
                when (event) {
                    SET_CURRENT_TAB -> activityViewModel.selectedMainTabInMainActivity.value =
                        value as Int
                }
            })
            masterSimpleInfo.observe(viewLifecycleOwner, { masterDto ->
                masterDto.requestMeasureYn?.let {
                    binding.switchCompat.isChecked = it
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.requestMasterSimpleInfo()
        viewModel.requestRequirementTotal()
        viewModel.initListUnread()
    }

    companion object {
        private const val TAG = "HomeFragment"

        fun newInstance() = HomeFragment()
    }
}