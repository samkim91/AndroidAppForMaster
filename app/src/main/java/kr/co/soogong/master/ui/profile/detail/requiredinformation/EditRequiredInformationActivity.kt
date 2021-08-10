package kr.co.soogong.master.ui.profile.detail.requiredinformation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.ApprovedCodeTable
import kr.co.soogong.master.data.model.profile.NotApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.databinding.ActivityEditRequiredInformationBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogBundle
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.GET_PROFILE_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.GET_PROFILE_SUCCESSFULLY
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.MASTER_APPROVED_STATUS
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.SAVE_MASTER_INFORMATION_FAILED
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
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.mutation
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditRequiredInformationActivity : BaseActivity<ActivityEditRequiredInformationBinding>(
    R.layout.activity_edit_required_information
) {
    private val viewModel: EditRequiredInformationViewModel by viewModels()

    private lateinit var naverMapHelper: NaverMapHelper

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
                        dialogBundle = BottomDialogBundle.getIncreasingYearBundle("career"),
                        itemClick = { _, value ->
                            if (viewModel.profile.value?.approvedStatus == ApprovedCodeTable.code) {
                                val dialog = CustomDialog(
                                    dialogData = DialogData.getConfirmingForRequiredDialogData(this@EditRequiredInformationActivity),
                                    yesClick = { viewModel.saveCareerPeriod(value) },
                                    noClick = { })

                                dialog.show(supportFragmentManager, dialog.tag)
                            } else {
                                viewModel.saveCareerPeriod(value)
                            }
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
                PermissionHelper.checkLocationPermission(
                    context = this@EditRequiredInformationActivity,
                    onGranted = {
                        val bottomDialog =
                            BottomDialogRecyclerView.newInstance(
                                dialogBundle = BottomDialogBundle.getServiceAreaBundle(),
                                itemClick = { _, radius ->
                                    if (::naverMapHelper.isInitialized) {        // 이미 맵이 초기화 되어있다면, 위치만 바꿔준다.
                                        naverMapHelper.setLocation(
                                            viewModel.requiredInformation.value?.coordinate,
                                            radius
                                        )
                                    } else {        // 맵이 초기화 되어 있지 않다면, 초기화한다.
                                        naverMapHelper = NaverMapHelper(
                                            context = this@EditRequiredInformationActivity,
                                            fragmentManager = supportFragmentManager,
                                            frameLayout = binding.serviceArea.mapFragment,
                                            coordinate = viewModel.requiredInformation.value?.coordinate,
                                            radius = radius,
                                        )
                                    }
                                    viewModel.requiredInformation.mutation {
                                        value?.serviceArea = radius
                                    }
                                    viewModel.saveServiceArea(radius)
                                }
                            )

                        bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                    },
                    onDenied = { }
                )
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
                GET_PROFILE_SUCCESSFULLY -> {
                    if (::naverMapHelper.isInitialized) {        // 이미 맵이 초기화 되어있다면, 위치만 바꿔준다.
                        naverMapHelper.setLocation(
                            viewModel.requiredInformation.value?.coordinate,
                            viewModel.requiredInformation.value?.serviceArea
                        )
                    } else {        // 맵이 초기화 되어 있지 않다면, 초기화한다.
                        naverMapHelper = NaverMapHelper(
                            context = this@EditRequiredInformationActivity,
                            fragmentManager = supportFragmentManager,
                            frameLayout = binding.serviceArea.mapFragment,
                            coordinate = viewModel.requiredInformation.value?.coordinate,
                            radius = viewModel.requiredInformation.value?.serviceArea,
                        )
                    }
                }
                SAVE_MASTER_INFORMATION_FAILED, GET_PROFILE_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })

        viewModel.event.observe(this, EventObserver { (event, value) ->
            when (event) {
                MASTER_APPROVED_STATUS -> {
                    when (value) {
                        NotApprovedCodeTable.code -> setLayoutForNotApprovedMaster()
                        RequestApproveCodeTable.code -> setLayoutForRequestApproveMaster()
                        else -> setLayoutForApprovedMaster()
                    }
                }
            }
        })
    }

    override fun onStart() {
        Timber.tag(TAG).d("onStart: ")
        super.onStart()
        viewModel.requestRequiredInformation()
    }

    private fun setLayoutForRequestApproveMaster() {
        bind {
            alertContainerToFillUpRequiredInformation.isVisible = true
            requiredProfileCardPercentage.isVisible = true
            groupSawCheckForConfirmedMaster.isVisible = false
            defaultButton.isVisible = true
            defaultButton.isEnabled = false
            defaultButton.text = getString(R.string.waiting_for_confirmation)
        }
        setRequirementInformationPercentage()
    }

    private fun setLayoutForApprovedMaster() {
        bind {
            alertContainerToFillUpRequiredInformation.isVisible = false
            requiredProfileCardPercentage.isVisible = false
            groupSawCheckForConfirmedMaster.isVisible = true
            defaultButton.isVisible = false
        }
    }

    private fun setLayoutForNotApprovedMaster() {
        bind {
            alertContainerToFillUpRequiredInformation.isVisible = true
            requiredProfileCardPercentage.isVisible = true
            defaultButton.isVisible = true
            defaultButton.isEnabled = true
            defaultButton.text = getString(R.string.request_confirmation)
        }
        setRequirementInformationPercentage()
    }

    private fun setRequirementInformationPercentage() {
        bind {
            val totalCount = 10
            var filledCount = 0

            with(viewModel.requiredInformation) {
                if (!value?.introduction.isNullOrEmpty()) filledCount++
                if (!value?.shopImages.isNullOrEmpty()) filledCount++
                if (!value?.businessUnitInformation?.businessType.isNullOrEmpty()) filledCount++
                if (value?.warrantyInformation?.warrantyPeriod != null) filledCount++
                if (!value?.career.isNullOrEmpty()) filledCount++
                if (!value?.tel.isNullOrEmpty()) filledCount++
                if (!value?.ownerName.isNullOrEmpty()) filledCount++
                if (!value?.majors.isNullOrEmpty()) filledCount++
                if (!value?.companyAddress?.roadAddress.isNullOrEmpty()) filledCount++
                if (value?.serviceArea != null) filledCount++
            }

            val percentage: Float = filledCount.toFloat() / totalCount.toFloat() * 100f

            requiredProfileCardPercentage.text =
                getString(R.string.percentage_of_required_information, percentage.toInt())

            if (percentage == 100.0f) {
                requiredProfileCardPercentage.setTextColor(
                    resources.getColor(
                        R.color.color_1FC472,
                        null
                    )
                )
            } else {
                requiredProfileCardPercentage.setTextColor(
                    resources.getColor(
                        R.color.color_FF711D,
                        null
                    )
                )
            }
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