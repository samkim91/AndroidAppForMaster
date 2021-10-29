package kr.co.soogong.master.ui.profile.detail.requiredinformation

import android.os.Bundle
import androidx.activity.viewModels
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
                        dialogBundle = BottomDialogBundle.getCareerYearBundle(),
                        itemClick = { _, value ->
                            if (viewModel.profile.value?.approvedStatus == ApprovedCodeTable.code) {
                                CustomDialog.newInstance(DialogData.getConfirmingForRequiredDialogData(
                                    this@EditRequiredInformationActivity))
                                    .let {
                                        it.setButtonsClickListener(
                                            onPositive = { viewModel.saveCareerPeriod(value) },
                                            onNegative = { }
                                        )
                                        it.show(supportFragmentManager, it.tag)
                                    }
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
                if (!isFulfilled(this@EditRequiredInformationActivity, binding, viewModel))
                    return@setOnClickListener

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
                        NotApprovedCodeTable.code -> {
                            setRequirementInformationPercentage(this, binding, viewModel)
                            setLayoutForNotApprovedMaster(this, binding)
                        }
                        RequestApproveCodeTable.code -> {
                            setRequirementInformationPercentage(this, binding, viewModel)
                            setLayoutForRequestApproveMaster(this, binding)
                        }
                        else -> setLayoutForApprovedMaster(binding)
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