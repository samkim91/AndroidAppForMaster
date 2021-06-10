package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep4Binding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class Step4Fragment : BaseFragment<FragmentSignUpStep4Binding>(
    R.layout.fragment_sign_up_step4
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            defaultButton.setOnClickListener {
                viewModel.ownerName.observe(viewLifecycleOwner, {
                    ownerName.alertVisible = it.isNullOrEmpty()
                })


                if (!ownerName.alertVisible) {
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    companion object {
        private const val TAG = "Step4Fragment"

        fun newInstance(): Step4Fragment {
            return Step4Fragment()
        }
    }
}