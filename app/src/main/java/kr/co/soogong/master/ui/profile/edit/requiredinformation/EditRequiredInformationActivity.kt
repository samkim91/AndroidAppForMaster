package kr.co.soogong.master.ui.profile.edit.requiredinformation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEditRequiredInformationBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogData
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
import kr.co.soogong.master.ui.profile.edit.requiredinformation.EditRequiredInformationViewModel.Companion.SAVE_CAREER_PERIOD_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.EditRequiredInformationViewModel.Companion.SAVE_CAREER_PERIOD_SUCCESSFULLY
import kr.co.soogong.master.ui.utils.NaverMapHelper
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_ADDRESS
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_BRIEF_INTRODUCTION
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_BUSINESS_REPRESENTATIVE_NAME
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_BUSINESS_TYPES
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_BUSINESS_UNIT_INFORMATION
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_COMPANY_IMAGE
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_PHONE_NUMBER
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_WARRANTY_INFORMATION
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.mutation
import kr.co.soogong.master.util.extension.toast
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
            diameter = viewModel.requiredInformation.value?.serviceArea
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

            briefIntroduction.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_BRIEF_INTRODUCTION)
            }

            representativeImages.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_COMPANY_IMAGE)
            }

            businessUnitInformation.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_BUSINESS_UNIT_INFORMATION)
            }

            warrantyInformation.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_WARRANTY_INFORMATION)
            }

            career.addDefaultButtonClickListener {
                val bottomDialog =
                    BottomDialogRecyclerView("경력", BottomDialogData.getWarrantyPeriodList(),
                        itemClick = { text, value ->
                            viewModel.saveCareerPeriod(text, value)
                        }
                    )

                bottomDialog.show(supportFragmentManager, bottomDialog.tag)
            }

            businessRepresentativeName.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_BUSINESS_REPRESENTATIVE_NAME)
            }

            phoneNumber.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_PHONE_NUMBER)
            }

            businessTypes.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_BUSINESS_TYPES)
            }

            address.addDefaultButtonClickListener {
                startActivityCommonCode(EDIT_ADDRESS)
            }

            serviceArea.addDefaultButtonClickListener {
                val bottomDialog =
                    BottomDialogRecyclerView("범위 선택", BottomDialogData.getServiceAreaList(),
                        itemClick = { _, diameter ->
                            naverMap.changeServiceArea(diameter)
                            viewModel.requiredInformation.mutation {
                                value?.serviceArea = diameter
                            }
                            viewModel.saveServiceArea(diameter)
                        }
                    )

                bottomDialog.show(supportFragmentManager, bottomDialog.tag)
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                SAVE_CAREER_PERIOD_SUCCESSFULLY -> {
                    viewModel.getRequiredInfo()
                }
                SAVE_CAREER_PERIOD_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    override fun onStart() {
        Timber.tag(TAG).d("onStart: ")
        super.onStart()
        viewModel.getRequiredInfo()
        naverMap

        if (viewModel.isApprovedMaster.value == true) setLayoutForApprovedMaster() else setPercentageText()
    }

    private fun setLayoutForApprovedMaster() {
        binding.requiredProfileCardPercentage.visibility = View.GONE
        binding.groupSawCheckForConfirmedMaster.visibility = View.VISIBLE
        binding.defaultButton.visibility = View.GONE
        binding.alertContainerToFillUpRequiredInformation.visibility = View.GONE
    }

    private fun setPercentageText() {
        val totalCount = 9
        var insertedCount = 0

        with(viewModel.requiredInformation) {
            if (!value?.briefIntroduction.isNullOrEmpty()) insertedCount++
            if (value?.representativeImages?.get(0)?.path.isNullOrEmpty()) insertedCount++
            if (!value?.businessUnitInformation?.businessUnitType.isNullOrEmpty()) insertedCount++
            if (!value?.warrantyInformation?.warrantyPeriod.isNullOrEmpty()) insertedCount++
            if (!value?.career.isNullOrEmpty()) insertedCount++
            if (!value?.businessRepresentativeName.isNullOrEmpty()) insertedCount++
            if (!value?.phoneNumber.isNullOrEmpty()) insertedCount++
            if (!value?.businessTypes.isNullOrEmpty()) insertedCount++
//            if(!value?.address.isNullOrEmpty()) insertedCount++
        }

        val percentage = insertedCount / totalCount * 100

        if (percentage == 100) {
            binding.requiredProfileCardPercentage.setTextColor(
                resources.getColor(
                    R.color.color_FF711D,
                    null
                )
            )
            binding.defaultButton.isEnabled = true
        } else {
            binding.requiredProfileCardPercentage.setTextColor(
                resources.getColor(
                    R.color.color_1FC472,
                    null
                )
            )
            binding.defaultButton.isEnabled = false
        }
        binding.requiredProfileCardPercentage.text = "${percentage}% 등록"
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