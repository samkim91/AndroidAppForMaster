package kr.co.soogong.master.ui.profile.edit.requiredinformation.briefintroduction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditBriefIntroductionBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.briefintroduction.EditBriefIntroductionViewModel.Companion.GET_BRIEF_INTRODUCTION_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.briefintroduction.EditBriefIntroductionViewModel.Companion.SAVE_BRIEF_INTRODUCTION_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.briefintroduction.EditBriefIntroductionViewModel.Companion.SAVE_BRIEF_INTRODUCTION_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditBriefIntroductionFragment : BaseFragment<FragmentEditBriefIntroductionBinding>(
    R.layout.fragment_edit_brief_introduction
) {
    private val viewModel: EditBriefIntroductionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.getBriefIntro()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            defaultButton.setOnClickListener {
                viewModel.saveBriefIntro()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_BRIEF_INTRODUCTION_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_BRIEF_INTRODUCTION_FAILED, GET_BRIEF_INTRODUCTION_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditBriefIntroductionFragment"

        fun newInstance() = EditBriefIntroductionFragment()
    }
}