package kr.co.soogong.master.ui.auth.signup.steps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpMajorBinding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uihelper.major.MajorActivityHelper
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
//                val data: Intent? = result.data
//                val selectedMajor: Major by lazy {
//                    data?.getParcelableExtra(BUNDLE_MAJOR) ?: Major(null, null)
//                }
//                MajorChipGroupHelper.makeEntryChipGroupWithSubtitleForMajor(
//                    layoutInflater = layoutInflater,
//                    container = binding.majorContainer,
//                    newMajor = selectedMajor,
//                    viewModelBusinessTypes = viewModel.majors
//                )

                result.data?.let { intent ->
                    MajorActivityHelper.getProjectsFromIntent(intent)?.let { projects ->
                        viewModel.projects.addAll(projects)
                    }
                }
            }
        }


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
                viewModel.projects.observe(viewLifecycleOwner, {
                    Timber.tag(TAG).d("defaultButton: ${it.size}")
                    major.alertVisible = it.isNullOrEmpty()
                })


                if (!major.alertVisible) {
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.projects.observe(viewLifecycleOwner, { projects ->
            binding.cgContainer.removeAllViews()        // update 가 일어날 때마다, 계속 chip 이 추가되기에 초기에 전체삭제

            projects.map { project ->
                binding.cgContainer.addView(
                    (layoutInflater.inflate(R.layout.chip_entry_layout,
                        binding.cgContainer,
                        false) as Chip).also { chip ->
                        chip.text = project.name
                        chip.setOnCloseIconClickListener {
                            viewModel.projects.remove(project)
                        }
                    }
                )
            }
        })
    }

    companion object {
        private const val TAG = "MajorFragment"

        fun newInstance(): MajorFragment {
            return MajorFragment()
        }
    }
}