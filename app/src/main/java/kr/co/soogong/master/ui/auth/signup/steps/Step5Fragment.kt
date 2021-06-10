package kr.co.soogong.master.ui.auth.signup.steps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.BusinessType
import kr.co.soogong.master.databinding.FragmentSignUpStep5Binding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.utility.BusinessTypeChipGroupHelper
import kr.co.soogong.master.uihelper.major.MajorActivityHelper
import kr.co.soogong.master.uihelper.major.MajorActivityHelper.BUNDLE_BUSINESS_TYPE
import timber.log.Timber

@AndroidEntryPoint
class Step5Fragment : BaseFragment<FragmentSignUpStep5Binding>(
    R.layout.fragment_sign_up_step5
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private var getBusinessTypeLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedBusinessType: BusinessType by lazy {
                    data?.getParcelableExtra(BUNDLE_BUSINESS_TYPE) ?: BusinessType(null, null)
                }
                BusinessTypeChipGroupHelper.makeEntryChipGroupWithSubtitleForBusinessTypes(
                    layoutInflater = layoutInflater,
                    container = binding.businessTypeContainer,
                    newBusinessType = selectedBusinessType,
                    viewModelBusinessTypes = viewModel.businessTypes
                )
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

            businessType.setButtonClickListener {
                getBusinessTypeLauncher.launch(
                    Intent(
                        MajorActivityHelper.getIntent(
                            requireContext()
                        )
                    )
                )
            }

            defaultButton.setOnClickListener {
                viewModel.businessTypes.observe(viewLifecycleOwner, {
                    businessType.alertVisible = it.isNullOrEmpty()
                })


                if (!businessType.alertVisible) {
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    companion object {
        private const val TAG = "Step5Fragment"

        fun newInstance(): Step5Fragment {
            return Step5Fragment()
        }
    }
}