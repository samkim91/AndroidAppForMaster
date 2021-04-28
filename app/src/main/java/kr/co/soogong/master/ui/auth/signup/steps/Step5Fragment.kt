package kr.co.soogong.master.ui.auth.signup.steps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.*
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class Step5Fragment : BaseFragment<FragmentSignUpStep5Binding>(
    R.layout.fragment_sign_up_step5
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private var getBusinessTypeLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Timber.tag(TAG).d("StartActivityForResult: $result")

                val data: Intent? = result.data

            }
        }

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
                viewModel.businessType.observe(viewLifecycleOwner, {
                    businessType.alertVisible = it.isNullOrEmpty()
                })


//                if (!signUpPassword.alertVisible && !signUpConfirmPassword.alertVisible){
                (activity as? SignUpActivity)?.moveToNext()
//                }
            }

            businessType.setButtonClickListener {
                getBusinessTypeLauncher.launch(Intent(CategoryActivityHelper.getIntent(requireContext())))
            }
        }
    }


    companion object {
        private const val TAG = "Step4Fragment"

        fun newInstance(): Step5Fragment {
            return Step5Fragment()
        }
    }
}