package kr.co.soogong.master.ui.profile.detail.requiredinformation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEditRequiredInformationBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogData
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.GET_PROFILE_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.GET_PROFILE_SUCCESSFULLY
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.SAVE_MASTER_INFORMATION_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.SAVE_MASTER_INFORMATION_SUCCESSFULLY
import kr.co.soogong.master.uihelper.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_ADDRESS
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_BUSINESS_UNIT_INFORMATION
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_INTRODUCTION
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_MAJOR
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_OWNER_NAME
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PHONE_NUMBER
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_SHOP_IMAGES
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_WARRANTY_INFORMATION
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.NaverMapHelper
import kr.co.soogong.master.utility.extension.mutation
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditRequiredInformationActivity : BaseActivity<ActivityEditRequiredInformationBinding>(
    R.layout.activity_edit_required_information
) {
    private val viewModel: EditRequiredInformationViewModel by viewModels()

    private val naverMap: NaverMapHelper by lazy {
        NaverMapHelper(
            context = this@EditRequiredInformationActivity,
            fragmentManager = supportFragmentManager,
            frameLayout = binding.serviceArea.mapFragment,
            coordinate = viewModel.requiredInformation.value?.coordinate,
            radius = viewModel.requiredInformation.value?.serviceArea
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = this@EditRequiredInformationActivity

            with(actionBar) {
                title.text = getString(R.string.title_edit_required_information_activity)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            introduction.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_INTRODUCTION)
            }

            shopImages.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_SHOP_IMAGES)
            }

            businessUnitInformation.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_BUSINESS_UNIT_INFORMATION)
            }

            warrantyInformation.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_WARRANTY_INFORMATION)
            }

            career.addDefaultButtonClickListener {
                val bottomDialog =
                    BottomDialogRecyclerView.newInstance(
                        title = BottomDialogData.insertingCareerTitle,
                        dialogData = BottomDialogData.getWarrantyPeriodList(),
                        itemClick = { _, value ->
                            viewModel.saveCareerPeriod(value)
                        }
                    )

                bottomDialog.show(supportFragmentManager, bottomDialog.tag)
            }

            ownerName.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_OWNER_NAME)
            }

            phoneNumber.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_PHONE_NUMBER)
            }

            major.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_MAJOR)
            }

            address.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_ADDRESS)
            }

            serviceArea.addDefaultButtonClickListener {
                val bottomDialog =
                    BottomDialogRecyclerView.newInstance(
                        title = BottomDialogData.choosingServiceAreaTitle,
                        dialogData = BottomDialogData.getServiceAreaList(),
                        itemClick = { _, radius ->
                            naverMap.changeServiceArea(radius)
                            viewModel.requiredInformation.mutation {
                                value?.serviceArea = radius
                            }
                            viewModel.saveServiceArea(radius)
                        }
                    )

                bottomDialog.show(supportFragmentManager, bottomDialog.tag)
            }

            defaultButton.setOnClickListener {
                viewModel.requestApprove()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                GET_PROFILE_SUCCESSFULLY -> setMasterApprovalLayout()
                SAVE_MASTER_INFORMATION_SUCCESSFULLY -> viewModel.requestRequiredInformation()
                SAVE_MASTER_INFORMATION_FAILED, GET_PROFILE_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    override fun onStart() {
        Timber.tag(TAG).d("onStart: ")
        super.onStart()
        viewModel.requestRequiredInformation()
        naverMap
    }

    private fun setMasterApprovalLayout() {
        if (viewModel.isApprovedMaster.value == true) setLayoutForApprovedMaster() else setPercentageText()
    }

    private fun setLayoutForApprovedMaster() {
        binding.requiredProfileCardPercentage.visibility = View.GONE
        binding.groupSawCheckForConfirmedMaster.visibility = View.VISIBLE
        binding.defaultButton.visibility = View.GONE
        binding.alertContainerToFillUpRequiredInformation.visibility = View.GONE
    }

    private fun setPercentageText() {
        val totalCount = 10
        var filledCount = 0

        with(viewModel.requiredInformation) {
            if (!value?.introduction.isNullOrEmpty()) filledCount++
            if (!value?.shopImages.isNullOrEmpty()) filledCount++
            if (!value?.businessUnitInformation?.businessType.isNullOrEmpty()) filledCount++
            if (value?.warrantyInformation?.warrantyPeriod != 0) filledCount++
            if (!value?.career.isNullOrEmpty()) filledCount++
            if (!value?.tel.isNullOrEmpty()) filledCount++
            if (!value?.ownerName.isNullOrEmpty()) filledCount++
            if (!value?.majors.isNullOrEmpty()) filledCount++
            if (!value?.companyAddress?.roadAddress.isNullOrEmpty()) filledCount++
            if (value?.serviceArea != null) filledCount++
        }

        val percentage: Float = filledCount.toFloat() / totalCount.toFloat() * 100f

        binding.requiredProfileCardPercentage.text = getString(R.string.percentage_of_required_information, percentage.toInt())

        if (percentage == 100.0f) {
            binding.requiredProfileCardPercentage.setTextColor(
                resources.getColor(
                    R.color.color_1FC472,
                    null
                )
            )
            binding.defaultButton.isEnabled = true
        } else {
            binding.requiredProfileCardPercentage.setTextColor(
                resources.getColor(
                    R.color.color_FF711D,
                    null
                )
            )
            binding.defaultButton.isEnabled = false
        }
    }

    private fun startActivityCommonCode(pageName: String) {
        startActivity(
            EditProfileContainerActivityHelper.getIntent(
                this@EditRequiredInformationActivity,
                pageName
            )
        )
    }

    companion object {
        private const val TAG = "EditRequiredInformationActivity"

    }
}