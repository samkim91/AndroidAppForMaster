package kr.co.soogong.master.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.databinding.FragmentHomeBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.requirement.RequirementViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {
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

            newRequirementsRecyclerView.adapter = SimpleRequirementCardAdapter(
                requireContext(), childFragmentManager, viewModel
            )
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
            masterSimpleInfo.observe(viewLifecycleOwner, { masterDto ->
                masterDto.requestMeasureYn?.let {
                    binding.switchCompat.isChecked = it
                }
                setVisibilityForRequestMeasure(masterDto)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.requestMasterSimpleInfo()
        viewModel.requestList()
    }

    private fun setVisibilityForRequestMeasure(masterDto: MasterDto) {
        Timber.tag(TAG).d("setVisibilityForRequestMeasure: ")
        with(binding) {
            if (masterDto.secretaryYn != true || masterDto.freeMeasureYn != true) {
                switchGroup.isVisible = false
                return
            }

            switchGroup.isVisible = true
        }
    }

    companion object {
        private const val TAG = "HomeFragment"

        fun newInstance(): HomeFragment = HomeFragment()
    }
}