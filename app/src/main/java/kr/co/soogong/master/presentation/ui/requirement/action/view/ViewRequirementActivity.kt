package kr.co.soogong.master.presentation.ui.requirement.action.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.common.dialog.bottomDialogBasic.BottomDialogBasic
import kr.co.soogong.master.presentation.ui.common.dialog.bottomDialogCountableEdittext.BottomDialogCountableEdittext
import kr.co.soogong.master.presentation.ui.common.dialog.bottomDialogCountableEdittext.BottomDialogData
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.requirement.action.view.ViewRequirementViewModel.Companion.ASK_FOR_REVIEW_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.requirement.action.view.ViewRequirementViewModel.Companion.RESULT_OF_ACCEPTING_MEASURE
import kr.co.soogong.master.presentation.ui.requirement.action.view.ViewRequirementViewModel.Companion.INVALID_REQUIREMENT
import kr.co.soogong.master.presentation.ui.requirement.action.view.ViewRequirementViewModel.Companion.NOT_APPROVED_MASTER
import kr.co.soogong.master.presentation.ui.requirement.action.view.ViewRequirementViewModel.Companion.REFUSE_TO_ESTIMATE_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.requirement.action.view.ViewRequirementViewModel.Companion.REQUEST_APPROVE_MASTER
import kr.co.soogong.master.presentation.ui.requirement.action.view.ViewRequirementViewModel.Companion.SHOW_ACCEPTING_ESTIMATION
import kr.co.soogong.master.presentation.ui.requirement.action.view.ViewRequirementViewModel.Companion.SHOW_CALL_CENTER_DESCRIPTION
import kr.co.soogong.master.presentation.ui.requirement.action.view.ViewRequirementViewModel.Companion.SHOW_CALL_TO_CLIENT
import kr.co.soogong.master.presentation.ui.requirement.action.view.ViewRequirementViewModel.Companion.SHOW_MASTER_MEMO
import kr.co.soogong.master.presentation.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class ViewRequirementActivity : BaseActivity<ActivityViewRequirementBinding>(
    R.layout.activity_view_requirement
) {
    private val viewModel: ViewRequirementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            vm = viewModel
            lifecycleOwner = this@ViewRequirementActivity

            abHeader.setIvBackClickListener { onBackPressed() }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.requirement.observe(this@ViewRequirementActivity) { requirement ->
            setFlexibleContainer(this, binding, requirement)
            setBottomButtons(this, viewModel, binding, requirement)
            showDialogForCallingCustomer(this, viewModel, requirement)
        }

        viewModel.event.observe(this, EventObserver { (event, value) ->
            when (event) {
                RESULT_OF_ACCEPTING_MEASURE ->
                    when (value) {
                        is String -> startActivity(CallToCustomerHelper.getIntent(value))
                        is Int -> toast(getString(value))
                    }
            }
        })

        viewModel.action.observe(this@ViewRequirementActivity, EventObserver { event ->
            when (event) {
                REFUSE_TO_ESTIMATE_SUCCESSFULLY -> {
                    toast(getString(R.string.refuse_to_estimate_or_measure_successfully_text))
                    onBackPressed()
                }
                INVALID_REQUIREMENT -> alertInvalidRequirement()
                ASK_FOR_REVIEW_SUCCESSFULLY -> toast(getString(R.string.ask_for_review_successful))
                NOT_APPROVED_MASTER -> alertNotApprovedMaster()
                REQUEST_APPROVE_MASTER -> alertRequestApproveMaster()
                SHOW_CALL_CENTER_DESCRIPTION -> showCallCenterDescription()
                SHOW_MASTER_MEMO -> showMasterMemo()
                SHOW_ACCEPTING_ESTIMATION -> showAcceptingEstimation()
                SHOW_CALL_TO_CLIENT -> showCallToClient()
                REQUEST_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    private fun showAcceptingEstimation() {
        DefaultDialog.newInstance(
            DialogData.getAcceptEstimation()
        ).let {
            it.setButtonsClickListener(
                onPositive = {
                    viewModel.acceptToMeasure()
                },
                onNegative = {}
            )
            it.show(supportFragmentManager, TAG)
        }
    }

    private fun showCallToClient() {
        DefaultDialog.newInstance(
            DialogData.getCallToCustomer()
        ).let {
            it.setButtonsClickListener(
                onPositive = {
                    viewModel.callToClient()
                },
                onNegative = {}
            )
            it.show(supportFragmentManager, TAG)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestRequirement()
    }

    private fun alertInvalidRequirement() {
        DefaultDialog.newInstance(
            dialogData = DialogData.getInvalidRequirement(),
            cancelable = false
        ).let {
            it.setButtonsClickListener(
                onPositive = { onBackPressed() },
                onNegative = { }
            )
            it.show(supportFragmentManager, TAG)
        }
    }

    private fun alertNotApprovedMaster() {
        DefaultDialog.newInstance(
            DialogData.getAskingFillRequiredProfile(),
            false
        ).let { dialog ->
            dialog.setButtonsClickListener(
                onPositive = { onBackPressed() },
                onNegative = { onBackPressed() }
            )
            dialog.show(supportFragmentManager, TAG)
        }
    }

    private fun alertRequestApproveMaster() {
        DefaultDialog.newInstance(
            DialogData.getWaitingUntilApproval(),
            false
        ).let { dialog ->
            dialog.setButtonsClickListener(
                onPositive = { onBackPressed() },
                onNegative = { onBackPressed() }
            )
            dialog.show(supportFragmentManager, TAG)
        }
    }

    private fun showCallCenterDescription() {
        BottomDialogBasic.newInstance(
            bottomDialogData = BottomDialogData.getCallCenterDescription(),
            content = viewModel.requirement.value?.callCenterDescription
        ).show(supportFragmentManager, TAG)
    }

    private fun showMasterMemo() {
        BottomDialogCountableEdittext.newInstance(
            bottomDialogData = BottomDialogData.getMemo(),
            content = viewModel.masterMemo.value
        ).let {
            it.setButtonsClickListener(
                onNegative = {},
                onPositive = { editedContent ->
                    viewModel.masterMemo.value = editedContent
                    viewModel.saveMasterMemo()
                },
                onCancel = { editedContent ->
                    viewModel.masterMemo.value = editedContent

                    DefaultDialog.newInstance(
                        DialogData.getConfirmingForIgnoreChange()
                    ).let { dialog ->
                        dialog.setButtonsClickListener(
                            onPositive = { showMasterMemo() },
                            onNegative = { }
                        )
                        dialog.show(supportFragmentManager, dialog.tag)
                    }
                }
            )
            it.show(supportFragmentManager, TAG)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // NOTE: ViewRequirement 화면에서 notification 으로 해당 requirement 에 접근했을 때, 화면을 refresh 해주기 위함
        intent?.let {
            viewModel.requirementId.value = ViewRequirementActivityHelper.getRequirementId(it)
            viewModel.requestRequirement()
        }
    }

    companion object {
        private val TAG = ViewRequirementActivity::class.java.simpleName
    }
}
