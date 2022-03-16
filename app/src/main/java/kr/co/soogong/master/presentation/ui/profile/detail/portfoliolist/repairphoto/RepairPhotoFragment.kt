package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.repairphoto

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.databinding.FragmentEditRepairPhotoBinding
import kr.co.soogong.master.domain.entity.profile.portfolio.RepairPhoto
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.DISMISS_LOADING
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.SHOW_LOADING
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.repairphoto.RepairPhotoViewModel.Companion.SAVE_PORTFOLIO_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.repairphoto.RepairPhotoViewModel.Companion.START_REPAIR_PHOTOS_PICKER
import kr.co.soogong.master.presentation.uihelper.common.MajorActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.addChipChoiceRounded
import kr.co.soogong.master.utility.extension.addChipEntryRounded
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class RepairPhotoFragment : BaseFragment<FragmentEditRepairPhotoBinding>(
    R.layout.fragment_edit_repair_photo
) {
    private val viewModel: RepairPhotoViewModel by viewModels()

    private val getMajorLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    MajorActivityHelper.getProjectsFromIntent(intent)?.let { projects ->
                        viewModel.project.value = projects.first()
                    }
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        initChipGroup()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            saidRepairPhoto.setImagesDeletableAdapter { viewModel.repairPhotos.removeAt(it) }

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                viewModel.title.observe(viewLifecycleOwner) {
                    stiTitle.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                }

                saidRepairPhoto.error =
                    if (viewModel.repairPhotos.getItemCount() == 0) getString(R.string.required_field_alert) else null

                viewModel.project.observe(viewLifecycleOwner) {
                    scgProject.error =
                        if (it == null) getString(R.string.required_field_alert) else null
                }

                if (stiTitle.error.isNullOrEmpty() && saidRepairPhoto.error.isNullOrEmpty() && scgProject.error.isNullOrEmpty()) {
                    viewModel.saveRepairPhoto()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_PORTFOLIO_SUCCESSFULLY -> activity?.finish()
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
                SHOW_LOADING -> showLoading(parentFragmentManager)
                DISMISS_LOADING -> dismissLoading()
                START_REPAIR_PHOTOS_PICKER -> {
                    PermissionHelper.checkImagePermission(context = requireContext(),
                        onGranted = {
                            TedImagePicker.with(requireContext())
                                .buttonBackground(R.drawable.shape_green_background_radius8)
                                .max((viewModel.maxPhoto - viewModel.repairPhotos.getItemCount()),
                                    resources.getString(R.string.maximum_images_count,
                                        viewModel.maxPhoto)
                                )
                                .startMultiImage { uris ->
                                    if (FileHelper.isImageExtension(uris, requireContext()) == false
                                    ) {
                                        requireContext().toast(getString(R.string.invalid_image_extension))
                                        return@startMultiImage
                                    }

                                    viewModel.updateImage = true
                                    viewModel.repairPhotos.addAll(uris.map {
                                        AttachmentDto(
                                            id = null,
                                            partOf = null,
                                            referenceId = null,
                                            description = null,
                                            s3Name = null,
                                            fileName = null,
                                            url = null,
                                            uri = it
                                        )
                                    })
                                }
                        },
                        onDenied = { })
                }
            }
        })

        viewModel.project.observe(viewLifecycleOwner) { project ->
            binding.scgProject.container.removeAllViews()

            if (project == null) {
                initChipGroup()
            } else {
                binding.scgProject.container.addChipEntryRounded(viewModel.project.value?.name!!) {
                    viewModel.project.value = null
                }
            }
        }
    }

    private fun initChipGroup() {
        binding.scgProject.container.addChipChoiceRounded(getString(R.string.project_name)) {
            getMajorLauncher.launch(MajorActivityHelper.getIntent(requireContext(),
                viewModel.maxProject))
        }
    }

    companion object {
        private val TAG = RepairPhotoFragment::class.java.name
        private const val PORTFOLIO = "PORTFOLIO"

        fun newInstance(repairPhoto: RepairPhoto? = null) = RepairPhotoFragment().apply {
            repairPhoto?.let {
                arguments = bundleOf(
                    PORTFOLIO to it
                )
            }
        }

        fun getRepairPhoto(savedStateHandle: SavedStateHandle): MutableLiveData<RepairPhoto> =
            savedStateHandle.getLiveData(PORTFOLIO)
    }
}