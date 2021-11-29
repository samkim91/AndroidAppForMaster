package kr.co.soogong.master.ui.profile.detail.businessunitinformation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.atomic.molecules.SubheadlineChipGroup
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.model.profile.*
import kr.co.soogong.master.databinding.FragmentEditBusinessUnitInformationBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.SAVE_MASTER_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditBusinessUnitInformationFragment :
    BaseFragment<FragmentEditBusinessUnitInformationBinding>(
        R.layout.fragment_edit_business_unit_information
    ) {
    private val viewModel: EditBusinessUnitInformationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestBusinessUnitInformation()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            buttonThemeGettingImages = ButtonTheme.OutlinedPrimary
            gettingImagesClickListener = View.OnClickListener {
                PermissionHelper.checkImagePermission(context = requireContext(),
                    onGranted = {
                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_green_background_radius8)
                            .start { uri ->
                                if (FileHelper.isImageExtension(uri, requireContext()) == false) {
                                    requireContext().toast(getString(R.string.invalid_image_extension))
                                    return@start
                                }

                                viewModel.businessRegistImage.clear()
                                viewModel.businessRegistImage.add(
                                    AttachmentDto(
                                        id = null,
                                        partOf = null,
                                        referenceId = null,
                                        description = null,
                                        s3Name = null,
                                        fileName = null,
                                        url = null,
                                        uri = uri)
                                )
                            }
                    },
                    onDenied = { })
            }

            sidRegisteredImages.setImagesDeletableAdapter { position ->
                viewModel.businessRegistImage.removeAt(position)
            }

            initChips()

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                viewModel.businessName.observe(viewLifecycleOwner, {
                    stiBusinessName.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                })

                viewModel.shopName.observe(viewLifecycleOwner, {
                    stiShopName.error =
                        if (it.length < 2 || it.length > 20) getString(R.string.required_value_between_2_and_20) else null
                })

                viewModel.businessNumber.observe(viewLifecycleOwner, {
                    stiBusinessNumber.error = when {
                        it.isNullOrBlank() -> getString(R.string.required_field_alert)
                        it.length > 10 -> getString(R.string.fill_text_under_10)
                        else -> null
                    }
                })

                if (viewModel.businessType.value == FreelancerCodeTable) {
                    if (!stiShopName.error.isNullOrBlank() || !stiBusinessNumber.error.isNullOrBlank()) return@setSaveButtonClickListener
                } else {
                    if (!stiBusinessName.error.isNullOrBlank() || !stiShopName.error.isNullOrBlank() || !stiBusinessNumber.error.isNullOrBlank()) return@setSaveButtonClickListener
                }

                if (viewModel.profile.value?.approvedStatus == ApprovedCodeTable.code) {
                    CustomDialog.newInstance(
                        dialogData = DialogData.getConfirmingForRequiredDialogData(requireContext()))
                        .let {
                            it.setButtonsClickListener(
                                onPositive = { viewModel.saveBusinessUnitInformation() },
                                onNegative = { }
                            )
                            it.show(parentFragmentManager, it.tag)
                        }
                } else {
                    viewModel.saveBusinessUnitInformation()
                }
            }
        }
    }

    private fun initChips() {
        SubheadlineChipGroup
            .initChips(requireContext(),
                layoutInflater,
                binding.scgBusinessType,
                viewModel.businessTypes.value?.map { it.inKorean }!!)
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.businessType.observe(viewLifecycleOwner, { type ->
            setLayoutByBusinessType(type)
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

    private fun setLayoutByBusinessType(businessType: CodeTable) {
        Timber.tag(TAG).d("setLayoutByBusinessType: ${businessType.code}")
        with(binding) {
            when (businessType) {
                FreelancerCodeTable -> {        // 프리랜서일 경우 레이아웃
                    stiBusinessName.isVisible = false
                    stiBusinessNumber.subheadline = getString(R.string.type_birthday)
                    stiBusinessNumber.hint = getString(R.string.type_birthday_hint)
                    sbbGettingImages.subheadline = getString(R.string.upload_social_security_image)
                    sbbGettingImages.hint = getString(R.string.upload_social_security_image_hint)
                }
                else -> {       // 법인 또는 개인사업자일 경우 레이아웃
                    stiBusinessName.isVisible = true
                    stiBusinessNumber.subheadline = getString(R.string.type_business_number)
                    stiBusinessNumber.hint = getString(R.string.type_business_number_hint)
                    sbbGettingImages.subheadline = getString(R.string.upload_business_regist_image)
                    sbbGettingImages.hint = ""
                }
            }
        }
    }

    companion object {
        private const val TAG = "EditBusinessUnitInformationFragment"

        fun newInstance() = EditBusinessUnitInformationFragment()
    }
}