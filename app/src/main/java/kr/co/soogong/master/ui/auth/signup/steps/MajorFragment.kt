package kr.co.soogong.master.ui.auth.signup.steps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.databinding.FragmentSignUpMajorBinding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uihelper.major.MajorActivityHelper
import kr.co.soogong.master.uihelper.major.MajorActivityHelper.BUNDLE_MAJOR
import kr.co.soogong.master.utility.MajorChipGroupHelper
import timber.log.Timber

@AndroidEntryPoint
class MajorFragment : BaseFragment<FragmentSignUpMajorBinding>(
    R.layout.fragment_sign_up_major
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private var getMajorLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedMajor: Major by lazy {
                    data?.getParcelableExtra(BUNDLE_MAJOR) ?: Major(null, null)
                }
                MajorChipGroupHelper.makeEntryChipGroupWithSubtitleForMajor(
                    layoutInflater = layoutInflater,
                    container = binding.majorContainer,
                    newMajor = selectedMajor,
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

            major.setButtonClickListener {
                getMajorLauncher.launch(
                    Intent(
                        MajorActivityHelper.getIntent(
                            requireContext()
                        )
                    )
                )
            }

            defaultButton.setOnClickListener {
                viewModel.businessTypes.observe(viewLifecycleOwner, {
                    major.alertVisible = it.isNullOrEmpty()
                })


                if (!major.alertVisible) {
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    companion object {
        private const val TAG = "Step5Fragment"

        fun newInstance(): MajorFragment {
            return MajorFragment()
        }
    }
}