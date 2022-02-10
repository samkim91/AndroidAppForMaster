package kr.co.soogong.master.utility.extension

import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.main.checkMasterApprovedStatus
import kr.co.soogong.master.presentation.ui.requirement.IRequirementViewModel
import kr.co.soogong.master.presentation.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.MeasureActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.WriteEstimationActivityHelper

fun AppCompatButton.setAcceptingEstimation(
    fragmentManager: FragmentManager,
    mainViewModel: MainViewModel,
    requirementId: Int,
) {
    this.isVisible = true
    this.text = context.getString(R.string.write_estimation)
    this.setOnClickListener {
        checkMasterApprovedStatus(fragmentManager, mainViewModel) {
            context.startActivity(
                WriteEstimationActivityHelper.getIntent(
                    context,
                    requirementId
                )
            )
        }
    }
}

fun AppCompatButton.setCallToClient(
    fragmentManager: FragmentManager,
    mainViewModel: MainViewModel,
    viewModel: IRequirementViewModel,
    phoneNumber: String,
    estimationId: Int,
    isCalled: Boolean,
) {
    this.isVisible = true

    if (isCalled) {
        this.text = context.getString(R.string.call_to_customer)
    } else {
        this.text = context.getString(R.string.call_to_customer_again)
        this.background = ResourcesCompat.getDrawable(resources,
            R.drawable.bg_solid_transparent_stroke_light_grey2_selector_radius30,
            null)
        this.setTextColor(ResourcesCompat.getColor(resources, R.color.grey_4, null))
    }

    this.setOnClickListener {
        checkMasterApprovedStatus(fragmentManager = fragmentManager,
            mainViewModel = mainViewModel) {
            viewModel.callToClient(estimationId)
            context.startActivity(CallToCustomerHelper.getIntent(phoneNumber))
        }
    }
}

fun AppCompatButton.setAcceptingMeasure(
    fragmentManager: FragmentManager,
    mainViewModel: MainViewModel,
    viewModel: IRequirementViewModel,
    estimationDto: EstimationDto,
) {
    this.isVisible = true
    this.text = context.getString(R.string.accept_measure)
    setOnClickListener {
        checkMasterApprovedStatus(fragmentManager, mainViewModel) {
            DefaultDialog.newInstance(
                DialogData.getAcceptMeasure()
            ).let {
                it.setButtonsClickListener(
                    onPositive = {
                        viewModel.respondToMeasure(estimationDto)
                    },
                    onNegative = { }
                )
                it.show(fragmentManager, it.tag)
            }
        }
    }
}

fun AppCompatButton.setSendMeasure(
    fragmentManager: FragmentManager,
    mainViewModel: MainViewModel,
    requirementId: Int,
) {
    this.isVisible = true
    this.text = context.getString(R.string.send_measure)
    this.setOnClickListener {
        checkMasterApprovedStatus(fragmentManager, mainViewModel) {
            context.startActivity(
                MeasureActivityHelper.getIntent(context, requirementId)
            )
        }
    }
}

fun AppCompatButton.setRepairDone(
    fragmentManager: FragmentManager,
    mainViewModel: MainViewModel,
    requirementId: Int,
) {
    this.isVisible = true
    this.text = context.getString(R.string.repair_done_text)
    this.setOnClickListener {
        checkMasterApprovedStatus(fragmentManager, mainViewModel) {
            context.startActivity(
                EndRepairActivityHelper.getIntent(
                    context,
                    requirementId
                )
            )
        }
    }
}

fun AppCompatButton.setAskForReview(
    fragmentManager: FragmentManager,
    mainViewModel: MainViewModel,
    viewModel: IRequirementViewModel,
    repairDto: RepairDto,
    isRequested: Boolean,
) {
    this.isVisible = true

    if (isRequested) {
        this.isEnabled = false
        this.text = context.getString(R.string.request_review_done)
        this.background = ResourcesCompat.getDrawable(resources,
            R.drawable.bg_solid_light_grey1_selector_radius30,
            null)
        this.setTextColor(ResourcesCompat.getColor(resources, R.color.grey_1, null))
    } else {
        this.text = context.getString(R.string.request_review)
    }

    this.setOnClickListener {
        checkMasterApprovedStatus(fragmentManager, mainViewModel) {
            viewModel.askForReview(repairDto)
        }
    }
}