package kr.co.soogong.master.presentation.ui.auth.signup.steps

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.databinding.FragmentMajorBinding
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_MAJOR
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.uihelper.common.MajorActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class MajorFragment : BaseFragment<FragmentMajorBinding>(
    R.layout.fragment_major
) {
    private val viewModel: SignUpViewModel by viewModels({ requireParentFragment() })

    private var getMajorLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    MajorActivityHelper.getProjectsFromIntent(intent)?.let { projects ->
                        viewModel.projects.addAllAsSet(
                            items = projects,
                            distinct = { list -> list.distinctBy { it.id } }
                        )
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
            buttonThemeAddingMajors = ButtonTheme.Primary
            addingMajorsClickListener = View.OnClickListener {
                getMajorLauncher.launch(MajorActivityHelper.getIntent(requireContext()))
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

        viewModel.validation.observe(viewLifecycleOwner, { validation ->
            if (validation == VALIDATE_MAJOR) {
                viewModel.projects.observe(viewLifecycleOwner, {
                    binding.sbbSelectMajors.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                })

                if (binding.sbbSelectMajors.error.isNullOrEmpty()) viewModel.moveToNext()
            }
        })
    }

    companion object {
        private const val TAG = "MajorFragment"

        fun newInstance() = MajorFragment()
    }
}