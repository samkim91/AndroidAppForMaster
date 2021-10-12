package kr.co.soogong.master.ui.profile.detail.freeMeasure

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditFreeMeasureBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.SAVE_MASTER_SUCCESSFULLY
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

            defaultButton.setOnClickListener {
                if (viewModel.agree.value == null || viewModel.disagree.value == null) return@setOnClickListener

                viewModel.saveFreeMeasure()
            }

            agreeCheckbox.setCheckClick {
                viewModel.agree.value = true
                viewModel.disagree.value = false
            }

            disagreeCheckbox.setCheckClick {
                viewModel.agree.value = false
                viewModel.disagree.value = true
            }
        }
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