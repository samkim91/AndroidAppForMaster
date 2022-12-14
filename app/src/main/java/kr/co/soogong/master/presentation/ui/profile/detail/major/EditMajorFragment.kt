package kr.co.soogong.master.presentation.ui.profile.detail.major

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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
import kr.co.soogong.master.utility.extension.addChipEntryRounded
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

                viewModel.saveMajor()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.projects.observe(viewLifecycleOwner) { projects ->
            binding.cgContainer.removeAllViews()    // update ??? ????????? ?????????, ?????? chip ??? ??????????????? ????????? ????????????

            projects.map { project ->
                binding.cgContainer.addChipEntryRounded(project.name){
                    viewModel.projects.remove(project)
                }
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