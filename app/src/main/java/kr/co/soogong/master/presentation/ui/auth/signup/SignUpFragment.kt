package kr.co.soogong.master.presentation.ui.auth.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpBinding
import kr.co.soogong.master.presentation.ui.auth.AuthContainerActivity
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    R.layout.fragment_sign_up
) {
    private val viewModel: SignUpViewModel by viewModels()

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

            abHeader.setIvBackClickListener { (activity as AuthContainerActivity).onBackPressed() }

            with(vpContainer) {
                isUserInputEnabled = false
                adapter = SignUpPagerAdapter(this@SignUpFragment, viewModel.totalPages)
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when(action) {
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })

        viewModel.event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
            when (event) {
                "test" -> binding.bBottom.isEnabled = value as Boolean
            }
        })

        viewModel.currentPage.observe(viewLifecycleOwner, {
            binding.vpContainer.currentItem = it
            setBottomButton(it)
        })
    }

    private fun setBottomButton(page: Int) {
        Timber.tag(TAG).d("setBottomButton: ")

        when (page) {
            0 -> binding.bBottom.text = getString(R.string.next)
            1 -> binding.bBottom.text = getString(R.string.sign_up)
        }
    }


    companion object {
        private const val TAG = "SignUpFragment"

        fun newInstance() = SignUpFragment()
    }
}