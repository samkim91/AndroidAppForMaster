package kr.co.soogong.master.presentation.ui.profile.detail.major

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditMajorBinding
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_SUCCESS
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.presentation.uihelper.common.MajorActivityHelper
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class EditMajorFragment : BaseFragment<FragmentEditMajorBinding>(
    R.layout.fragment_edit_major
) {

    private val viewModel: EditMajorViewModel by viewModels()
    private val editProfileContainerViewModel: EditProfileContainerViewModel by activityViewModels()

    private val getMajorLauncher =
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

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                viewModel.projects.observe(viewLifecycleOwner) {
                    sbbSelectMajors.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                }

                if (!sbbSelectMajors.error.isNullOrEmpty()) return@setSaveButtonClickListener

                // 시공 가능 업종을 수정하면, 프로필의 승인상태가 변경된다. 따라서 마스터 승인상태에 따라 아래 코드가 실행
                if (viewModel.profile.value?.approvedStatus == CodeTable.APPROVED.code) {
                    DefaultDialog.newInstance(
                        DialogData.getConfirmingForLimitedService())
                        .let {
                            it.setButtonsClickListener(
                                onPositive = { viewModel.saveMajor() },
                                onNegative = { }
                            )
                            it.show(parentFragmentManager, it.tag)
                        }
                } else {
                    viewModel.saveMajor()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.projects.observe(viewLifecycleOwner) { projects ->
            binding.cgContainer.removeAllViews()    // update 가 일어날 때마다, 계속 chip 이 추가되기에 초기에 전체삭제

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
        }

        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                REQUEST_SUCCESS -> editProfileContainerViewModel.setAction(
                    REQUEST_SUCCESS)
            }
        })
    }

    companion object {
        private const val TAG = "EditMajorFragment"

        fun newInstance() = EditMajorFragment()
    }
}