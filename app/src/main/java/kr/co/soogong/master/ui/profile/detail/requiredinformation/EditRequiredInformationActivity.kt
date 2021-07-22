package kr.co.soogong.master.ui.profile.detail.requiredinformation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.NotApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.databinding.ActivityEditRequiredInformationBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogData
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.GET_PROFILE_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.GET_PROFILE_SUCCESSFULLY
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel.Companion.MASTER_SUBSCRIPTION_PLAN
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
                PermissionHelper.checkLocationPermission(
                    context = this@EditRequiredInformationActivity,
                    onGranted = {
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
                    naverMap
                }
                SAVE_MASTER_INFORMATION_FAILED, GET_PROFILE_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })

        viewModel.event.observe(this, EventObserver { (event, value) ->
            when(event) {
                MASTER_SUBSCRIPTION_PLAN -> {
                    when(value) {
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
            requiredProfileCardPercentage.visibility = View.GONE
            groupSawCheckForConfirmedMaster.visibility = View.GONE
            defaultButton.visibility = View.GONE
            alertContainerToFillUpRequiredInformation.visibility = View.GONE
            textSawCheckForConfirmedMaster.text = getString(R.string.waiting_for_confirmation)
            textSawCheckForConfirmedMaster.setTextColor(getColor(R.color.color_FF711D))
        }
    }

    private fun setLayoutForApprovedMaster() {
        bind {
            requiredProfileCardPercentage.visibility = View.GONE
            groupSawCheckForConfirmedMaster.visibility = View.VISIBLE
            defaultButton.visibility = View.GONE
            alertContainerToFillUpRequiredInformation.visibility = View.GONE
        }
    }

    private fun setLayoutForNotApprovedMaster() {
        bind {
            alertContainerToFillUpRequiredInformation.isVisible = true
            defaultButton.isVisible = true

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

            requiredProfileCardPercentage.text = getString(R.string.percentage_of_required_information, percentage.toInt())

            if (percentage == 100.0f) {
                requiredProfileCardPercentage.setTextColor(
                    resources.getColor(
                        R.color.color_1FC472,
                        null
                    )
                )
                defaultButton.isEnabled = true
            } else {
                requiredProfileCardPercentage.setTextColor(
                    resources.getColor(
                        R.color.color_FF711D,
                        null
                    )
                )
                defaultButton.isEnabled = false
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