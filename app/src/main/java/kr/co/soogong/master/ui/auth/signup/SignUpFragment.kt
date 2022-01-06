package kr.co.soogong.master.ui.auth.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpBinding
import kr.co.soogong.master.ui.auth.AuthContainerActivity
import kr.co.soogong.master.ui.base.BaseFragment
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

            abpHeader.setButtonBackClickListener { (activity as AuthContainerActivity).onBackPressed() }

            with(vpContainer) {
                isUserInputEnabled = false
                adapter = SignUpPagerAdapter(this@SignUpFragment)
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.indicator.observe(viewLifecycleOwner, {
            binding.vpContainer.currentItem = it
        })
    }

    companion object {
        private const val TAG = "SignUpFragment"

        fun newInstance() = SignUpFragment()
    }
}