package kr.co.soogong.master.ui.profile.detail.requiredinformation.businessunitinformation

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.ApprovedCodeTable
import kr.co.soogong.master.data.model.profile.CorporateCodeTable
import kr.co.soogong.master.data.model.profile.FreelancerCodeTable
import kr.co.soogong.master.data.model.profile.SoleCodeTable
import kr.co.soogong.master.databinding.FragmentEditBusinessUnitInformationBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.detail.requiredinformation.businessunitinformation.EditBusinessUnitInformationViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.businessunitinformation.EditBusinessUnitInformationViewModel.Companion.SAVE_BUSINESS_INFORMATION_SUCCESSFULLY
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

            initChips()

            cameraIconForBusinessRegistImage.setOnClickListener {
                PermissionHelper.checkImagePermission(context = requireContext(),
                    onGranted = {
                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_fill_green_background)
                            .start { uri ->
                                if (FileHelper.isImageExtension(uri, requireContext()) == false) {
                                    requireContext().toast(getString(R.string.invalid_image_extension))
                                    return@start
                                }

                                viewModel.businessRegistImage.value = uri
                            }
                    },
                    onDenied = { })
            }

            defaultButton.setOnClickListener {
                viewModel.businessName.observe(viewLifecycleOwner, {
                    businessName.alertVisible = it.isNullOrEmpty()
                })

                viewModel.shopName.observe(viewLifecycleOwner, {
                    shopName.alertVisible = it.length < 2 || it.length > 20
                })

                viewModel.businessNumber.observe(viewLifecycleOwner, {
                    businessNumber.alertVisible = it.isNullOrEmpty()
                })

                viewModel.businessRegistImage.observe(viewLifecycleOwner, {
                    alert.visibility = if (it == Uri.EMPTY) View.VISIBLE else View.GONE
                })

                if (viewModel.businessType.value == FreelancerCodeTable.code) {
                    if (shopName.alertVisible || businessNumber.alertVisible || alert.isVisible) return@setOnClickListener
                } else {
                    if (businessName.alertVisible || shopName.alertVisible || businessNumber.alertVisible || alert.isVisible) return@setOnClickListener
                }

                if (viewModel.profile.value?.approvedStatus == ApprovedCodeTable.code) {
                    val dialog = CustomDialog.newInstance(
                        dialogData = DialogData.getConfirmingForRequiredDialogData(requireContext()),
                        yesClick = { viewModel.saveBusinessUnitInformation() },
                        noClick = { })

                    dialog.show(parentFragmentManager, dialog.tag)
                } else {
                    viewModel.saveBusinessUnitInformation()
                }
            }
        }
    }

    private fun initChips() {
        bind {
            BusinessUnitInformationChipGroupHelper(layoutInflater, businessUnitType)

            businessUnitType.addCheckedChangeListener { group, position ->
                when (position) {
                    group.getChildAt(2).id -> {     // 프리랜서일 경우 레이아웃
                        viewModel.businessType.value = FreelancerCodeTable.code
                        businessName.isVisible = false
                        shopName.subTitleVisible = true
                        businessNumber.title = getString(R.string.requesting_birthday_label)
                        businessRegistImageHolderLabel.text = getString(R.string.identical_image_holder_label)
                    }
                    else -> {       // 사업자일 경우 레이아웃
                        viewModel.businessType.value =
                            if (position == group.getChildAt(0).id) SoleCodeTable.code else CorporateCodeTable.code
                        businessName.isVisible = true
                        shopName.subTitleVisible = false
                        businessNumber.title = getString(R.string.requesting_business_number_label)
                        businessRegistImageHolderLabel.text = getString(R.string.business_identical_image_holder_label)
                    }
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.businessType.observe(viewLifecycleOwner, { businessType ->
            binding.businessUnitType.chipGroup.children.forEach {
                val chip = it as? Chip
                if (chip?.text.toString() == businessType) chip?.isChecked = true
            }
        })
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_BUSINESS_INFORMATION_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditBusinessUnitInformationFragment"

        fun newInstance() = EditBusinessUnitInformationFragment()
    }
}