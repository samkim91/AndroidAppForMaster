package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpPhoneNumberBinding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.CHECK_PHONE_NUMBER_FAILED
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.PHONE_NUMBER_EXIST
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.PHONE_NUMBER_NOT_EXIST
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class PhoneNumberFragment : BaseFragment<FragmentSignUpPhoneNumberBinding>(
    R.layout.fragment_sign_up_phone_number
) {
    private val viewModel: SignUpViewModel by activityViewModels()

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

            defaultButton.setOnClickListener {
                viewModel.tel.observe(viewLifecycleOwner, {
                    phoneNumber.alertVisible = it.isNullOrEmpty() || it.length < 10
                })

                if (!phoneNumber.alertVisible) {
                    viewModel.checkUserExist()
                }
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                PHONE_NUMBER_EXIST -> {
                    showDialog()
                }
                PHONE_NUMBER_NOT_EXIST -> {
                    (activity as? SignUpActivity)?.moveToNext()
                }
                CHECK_PHONE_NUMBER_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    private fun showDialog() {
        val dialog = CustomDialog(
            DialogData.getExistentUserDialogData(requireContext()),
            yesClick = { (activity as? SignUpActivity)?.onBackPressedWithoutDialog() },
            noClick = { })

        dialog.show(parentFragmentManager, dialog.tag)
    }

    companion object {
        private const val TAG = "Step1Fragment"

        fun newInstance(): PhoneNumberFragment {
            return PhoneNumberFragment()
        }
    }
}

/*
* todo.. 마스터 로그인/회원가입 관련
* 로그인 화면에서 휴대폰 번호로 기존 가입자인지 확인한다.
* 가입자면 인증번호 요청
* 가입자가 아니면 회원가입 안내
*
* 회원가입 화면에서 휴대폰 번호로 기존 가입자인지 확인한다.
* 가입자면 로그인으로 이동
* 가입자가 아니면 회원가입 진행
*
* */