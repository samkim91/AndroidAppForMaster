package kr.co.soogong.master.presentation.ui.auth.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignInBinding
import kr.co.soogong.master.presentation.ui.auth.AuthContainerActivity
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.uihelper.main.MainActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(
    R.layout.fragment_sign_in
) {
    private val viewModel: SignInViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserver()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            authVm = authViewModel
            lifecycleOwner = viewLifecycleOwner

            abHeader.setIvBackClickListener { (activity as AuthContainerActivity).onBackPressed() }
        }
    }

    private fun registerEventObserver() {
        Timber.tag(TAG).d("registerEventObserver: ")

        authViewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                AuthViewModel.REQUIRED_TEL -> binding.tilPhoneNumber.error =
                    getString(R.string.invalid_phone_number)
                AuthViewModel.REQUIRED_CODE ->
                    binding.tilCode.error = getString(R.string.invalid_certification_code)

                AuthViewModel.CLEAR_ERROR -> {
                    binding.tilPhoneNumber.error = null
                    binding.tilCode.error = null
                }

                AuthViewModel.CREDENTIAL_CODE_REQUESTED -> requireContext().toast(getString(R.string.certification_code_requested))

                AuthViewModel.TRY_AGAIN -> requireContext().toast(getString(R.string.try_again))
                AuthViewModel.VERIFIED_SUCCESSFUL -> {
                    viewModel.signInByTel(authViewModel.tel.value!!)
                }
                AuthViewModel.VERIFIED_FAILED -> requireContext().toast(getString(R.string.wrong_certification_code))

                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })

        authViewModel.event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
            when (event) {
                AuthViewModel.EXIST_USER -> {
                    (value as Boolean).run {
                        if (this) authViewModel.requestCertificationCode() else binding.tilPhoneNumber.error = getString(R.string.alert_unknown_user)
                    }
                }
            }
        })

        authViewModel.certificationCode.observe(viewLifecycleOwner) {
            binding.bSignIn.isEnabled = it.length > 5
        }

        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                SignInViewModel.SIGN_IN_SUCCESSFULLY -> {
                    startActivity(MainActivityHelper.getIntent(requireContext()))
                }
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private val TAG = SignInFragment::class.java.simpleName

        fun newInstance() = SignInFragment()
    }
}