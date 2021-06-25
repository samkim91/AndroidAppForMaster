package kr.co.soogong.master.ui.profile.detail.requiredinformation.introduction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditIntroductionBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.detail.requiredinformation.introduction.EditIntroductionViewModel.Companion.GET_INTRODUCTION_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.introduction.EditIntroductionViewModel.Companion.SAVE_INTRODUCTION_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.introduction.EditIntroductionViewModel.Companion.SAVE_INTRODUCTION_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditIntroductionFragment : BaseFragment<FragmentEditIntroductionBinding>(
    R.layout.fragment_edit_introduction
) {
    private val viewModel: EditIntroductionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestIntroduction()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            defaultButton.setOnClickListener {
                viewModel.introduction.observe(viewLifecycleOwner, {
                    introduction.alertVisible = it.length < 10
                })

                if (!introduction.alertVisible) viewModel.saveIntroduction()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_INTRODUCTION_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_INTRODUCTION_FAILED, GET_INTRODUCTION_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditIntroductionFragment"

        fun newInstance() = EditIntroductionFragment()
    }
}