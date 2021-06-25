package kr.co.soogong.master.ui.profile.detail.email

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditEmailBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogData
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
import kr.co.soogong.master.ui.profile.detail.email.EditEmailViewModel.Companion.GET_EMAIL_FAILED
import kr.co.soogong.master.ui.profile.detail.email.EditEmailViewModel.Companion.SAVE_EMAIL_FAILED
import kr.co.soogong.master.ui.profile.detail.email.EditEmailViewModel.Companion.SAVE_EMAIL_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import kr.co.soogong.master.utility.validation.ValidationHelper
import timber.log.Timber

@AndroidEntryPoint
class EditEmailFragment :
    BaseFragment<FragmentEditEmailBinding>(
        R.layout.fragment_edit_email
    ) {
    private val viewModel: EditEmailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestEmail()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            defaultButton.setOnClickListener {
                viewModel.localPart.observe(viewLifecycleOwner, {
                    email.alertVisible = !ValidationHelper.isValidLocalPart(it)
                })

                // 하나의 alert text에 두개 모두를 만족해야 패스할 수 있는 방법을 찾아봐야함 ..
                viewModel.domain.value?.let {
                    if(!ValidationHelper.isValidDomain(it)) email.alertVisible = true
                }

                if (!email.alertVisible) viewModel.saveEmailInfo()
            }

            email.addDropdownClickListener {
                val bottomDialog =
                    BottomDialogRecyclerView("이메일 주소", BottomDialogData.getEmailDomains(),
                        itemClick = { domain, _ ->
                            if(domain != "이메일 직접입력") {
                                viewModel.domain.value = domain
                                email.secondDetailView.hint = getString(R.string.email_domain_default_hint_text)
                                email.secondDetailView.isEnabled = false
                            } else {
                                viewModel.domain.value = ""
                                email.secondDetailView.hint = getString(R.string.email_domain_type_hint_text)
                                email.secondDetailView.isEnabled = true
                            }
                        }
                    )

                bottomDialog.show(parentFragmentManager, bottomDialog.tag)
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_EMAIL_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_EMAIL_FAILED, GET_EMAIL_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditEmailFragment"

        fun newInstance() = EditEmailFragment()
    }
}