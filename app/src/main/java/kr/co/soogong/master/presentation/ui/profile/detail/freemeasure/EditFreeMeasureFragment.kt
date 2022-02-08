package kr.co.soogong.master.presentation.ui.profile.detail.freemeasure

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.presentation.atomic.molecules.SubheadlineChipGroup
import kr.co.soogong.master.databinding.FragmentEditFreeMeasureBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerViewModel.Companion.SAVE_MASTER_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditFreeMeasureFragment : BaseFragment<FragmentEditFreeMeasureBinding>(
    R.layout.fragment_edit_free_measure
) {
    private val viewModel: EditFreeMeasureViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestFreeMeasure()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            initChips()

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                if (viewModel.freeMeasure.value != null) viewModel.saveFreeMeasure()
            }
        }
    }

    private fun initChips() {
        SubheadlineChipGroup
            .initChips(
                requireContext(),
                layoutInflater,
                binding.scgFreeMeasure,
                viewModel.freeMeasureOptions.value?.map { it.inKorean }!!
            )
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_MASTER_SUCCESSFULLY -> activity?.onBackPressed()
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "EditIntroductionFragment"

        fun newInstance() = EditFreeMeasureFragment()
    }
}