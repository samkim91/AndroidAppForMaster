package kr.co.soogong.master.ui.profile.edit.requiredinformation.phonenumber

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditPhoneNumberBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.phonenumber.EditPhoneNumberViewModel.Companion.SAVE_PHONE_NUMBER_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.phonenumber.EditPhoneNumberViewModel.Companion.SAVE_PHONE_NUMBER_SUCCESSFULLY
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditPhoneNumberFragment :
    BaseFragment<FragmentEditPhoneNumberBinding>(
        R.layout.fragment_edit_phone_number
    ) {
    private val viewModel: EditPhoneNumberViewModel by viewModels()

    private val timer = object : CountDownTimer(180000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.timerView.text =
                "${millisUntilFinished / 1000 / 60}:${millisUntilFinished / 1000 % 60}"
        }

        override fun onFinish() {
            binding.alertExpiredCertificationTime.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            phoneNumber.setButtonClickListener {
                requestCertificationCode { requireContext().toast(getString(R.string.certification_code_requested)) }.let {
                    if(!binding.phoneNumber.alertVisible) viewModel.changeEnabled()
                }
            }

            requestCertificationCodeAgainText.setOnClickListener {
                requestCertificationCode { requireContext().toast(getString(R.string.certification_code_requested_again)) }
            }

            defaultButton.setOnClickListener {
                viewModel.certificationCode.observe(viewLifecycleOwner, {
                    alertInvalidCertificationCode.isVisible = it.length < 6
                })

                if (!alertWrongCertificationCode.isVisible && !alertExpiredCertificationTime.isVisible
                    && !alertInvalidCertificationCode.isVisible && !alertEmptyCertificationCode.isVisible) viewModel.requestConfirmCertificationCode()
            }
        }
    }

    private fun requestCertificationCode(toast: () -> Unit) {
        bind {
            viewModel.phoneNumber.observe(viewLifecycleOwner, {
                phoneNumber.alertVisible = it.length < 11
            })

            if (!phoneNumber.alertVisible) {
                startTimer()
                viewModel.requestCertificationCode()
                toast()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        bind {
            viewModel.isEnabled.observe(viewLifecycleOwner, { isEnabled ->
                phoneNumber.setFirstEditTextEnable = !isEnabled
                phoneNumber.buttonColor = isEnabled
                phoneNumber.buttonBackground = isEnabled
                phoneNumber.buttonText =
                    if (!isEnabled) getString(R.string.certification_text) else getString(R.string.retype_text)
                certificationCodeContainer.visibility = if (isEnabled) View.VISIBLE else View.GONE
                defaultButton.isEnabled = isEnabled
            })
        }

        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_PHONE_NUMBER_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_PHONE_NUMBER_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    private fun startTimer() {
        // Todo.. 인증번호 요청/재요청에 대한 event callback으로 실행되도록 변경해야함.
        binding.alertExpiredCertificationTime.visibility = View.GONE
        timer.start()
    }

    private fun stopTimer() {
        timer.cancel()
    }

    companion object {
        private const val TAG = "EditPhoneNumberViewModel"

        fun newInstance() = EditPhoneNumberFragment()
    }
}