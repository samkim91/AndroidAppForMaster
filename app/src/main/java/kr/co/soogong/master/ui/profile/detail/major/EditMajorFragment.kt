package kr.co.soogong.master.ui.profile.detail.major

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.data.model.profile.ApprovedCodeTable
import kr.co.soogong.master.databinding.FragmentEditMajorBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.SAVE_MASTER_SUCCESSFULLY
import kr.co.soogong.master.uihelper.major.MajorActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.MajorChipGroupHelper
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditMajorFragment : BaseFragment<FragmentEditMajorBinding>(
    R.layout.fragment_edit_major
) {
    private val viewModel: EditMajorViewModel by viewModels()

    private var getMajorLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedMajor: Major by lazy {
                    data?.getParcelableExtra(MajorActivityHelper.BUNDLE_MAJOR)
                        ?: Major(null, null)
                }
                MajorChipGroupHelper.makeEntryChipGroupWithSubtitleForMajor(
                    layoutInflater = layoutInflater,
                    container = binding.majorContainer,
                    newMajor = selectedMajor,
                    viewModelBusinessTypes = viewModel.majors
                )
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestMajor()

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
                viewModel.majors.observe(viewLifecycleOwner, {
                    major.alertVisible = it.isNullOrEmpty()
                })

                if (major.alertVisible) return@setOnClickListener

                if (viewModel.profile.value?.approvedStatus == ApprovedCodeTable.code) {
                    CustomDialog.newInstance(
                        DialogData.getConfirmingForRequiredDialogData(requireContext()))
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
        viewModel.majors.observe(viewLifecycleOwner, {
            MajorChipGroupHelper.addMajorToContainer(
                layoutInflater = layoutInflater,
                container = binding.majorContainer,
                majorList = viewModel.majors
            )
        })

        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_MASTER_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditMajorFragment"

        fun newInstance() = EditMajorFragment()
    }
}