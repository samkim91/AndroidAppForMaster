package kr.co.soogong.master.ui.auth.signup.step1

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep1Binding
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class Step1Fragment : BaseFragment<FragmentSignUpStep1Binding>(
    R.layout.fragment_sign_up_step1
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = viewLifecycleOwner


        }

    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    companion object {
        private const val TAG = "Step1Fragment"

        fun newInstance(): Step1Fragment {
            return Step1Fragment()
        }
    }

}