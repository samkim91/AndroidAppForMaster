package kr.co.soogong.master.ui.profile.edit.requiredinformation.businessunitinformation

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
import kr.co.soogong.master.databinding.FragmentEditBusinessUnitInformationBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.businessunitinformation.EditBusinessUnitInformationViewModel.Companion.SAVE_BUSINESS_UNIT_INFORMATION_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.businessunitinformation.EditBusinessUnitInformationViewModel.Companion.SAVE_BUSINESS_UNIT_INFORMATION_SUCCESSFULLY
import kr.co.soogong.master.ui.utils.PermissionHelper
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
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
        viewModel.getBusinessUnitInfo()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            initChips()

            cameraIconForIdenticalImage.setOnClickListener {
                PermissionHelper.checkImagePermission(context = requireContext(),
                    onGranted = {
                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_fill_green_background)
                            .start { uri ->
                                viewModel.identicalImage.value = uri
                            }
                    },
                    onDenied = {
                        requireContext().toast(getString(R.string.permission_denied_message))
                    })
            }

            defaultButton.setOnClickListener {
                viewModel.businessUnitName.observe(viewLifecycleOwner, {
                    businessUnitName.alertVisible = it.isNullOrEmpty()
                })

                viewModel.identicalNumber.observe(viewLifecycleOwner, {
                    identicalNumber.alertVisible = it.isNullOrEmpty()
                })

                viewModel.identicalImage.observe(viewLifecycleOwner, {
                    alert.visibility = if(it == Uri.EMPTY) View.VISIBLE else View.GONE
                })

                viewModel.birthday.observe(viewLifecycleOwner, {
                    birthday.alertVisible = it.isNullOrEmpty()
                })

                if(viewModel.businessUnitType.value == "프리랜서"){
                    if(!birthday.alertVisible) viewModel.saveBusinessUnitInfo()
                } else {
                    if(!businessUnitName.alertVisible && !identicalNumber.alertVisible && !alert.isVisible) viewModel.saveBusinessUnitInfo()
                }
            }
        }
    }

    private fun initChips() {
        bind {
            BusinessUnitInformationChipGroupHelper(layoutInflater, businessUnitType)

            businessUnitType.addCheckedChangeListener { group, position ->
                when (position) {
                    group.getChildAt(0).id, group.getChildAt(1).id -> {
                        viewModel.businessUnitType.value =
                            if (position == group.getChildAt(0).id) "개인사업자" else "법인사업자"
                        businessUnitGroup.visibility = View.VISIBLE
                        birthday.visibility = View.GONE
                    }
                    group.getChildAt(2).id -> {
                        viewModel.businessUnitType.value = "프리랜서"
                        businessUnitGroup.visibility = View.GONE
                        birthday.visibility = View.VISIBLE
                    }
                    else -> {
                        viewModel.businessUnitType.value = ""
                        businessUnitGroup.visibility = View.GONE
                        birthday.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.businessUnitType.observe(viewLifecycleOwner, { businessType ->
            binding.businessUnitType.chipGroup.children.forEach {
                val chip = it as? Chip
                if (chip?.text.toString() == businessType) chip?.isChecked = true
            }
        })
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when(event) {
                SAVE_BUSINESS_UNIT_INFORMATION_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_BUSINESS_UNIT_INFORMATION_FAILED -> {
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