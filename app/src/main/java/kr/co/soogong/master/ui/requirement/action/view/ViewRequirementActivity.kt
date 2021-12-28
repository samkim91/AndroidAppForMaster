package kr.co.soogong.master.ui.requirement.action.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.*
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.DefaultDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getExpiredRequestConsultDialogData
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.ASK_FOR_REVIEW_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.CALL_TO_CUSTOMER_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.INVALID_REQUIREMENT
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.NOT_APPROVED_MASTER
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.REFUSE_TO_ESTIMATE_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.RESPOND_TO_MEASURE_SUCCESSFULLY
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.action.*
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber
import java.util.*

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

            abHeader.setButtonBackClickListener {
                onBackPressed()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.requirement.observe(this@ViewRequirementActivity, { requirement ->
            if (!isValidRequirement(requirement)) return@observe
            setFlexibleContainer(this, binding, requirement)
            setBottomButtons(this, viewModel, binding, requirement)
            showDialogForCallingCustomer(requirement)
        })

        viewModel.action.observe(this@ViewRequirementActivity, EventObserver { event ->
            when (event) {
                REFUSE_TO_ESTIMATE_SUCCESSFULLY -> {
                    toast(getString(R.string.refuse_to_estimate_or_measure_successfully_text))
                    onBackPressed()
                }
                INVALID_REQUIREMENT -> onBackPressed()
                CALL_TO_CUSTOMER_SUCCESSFULLY -> viewModel.requirement.value?.let {
                    startActivity(CallToCustomerHelper.getIntent(it.phoneNumber))
                }
                // 화면 리프레시 하고 다이얼로그 띄우기
                RESPOND_TO_MEASURE_SUCCESSFULLY -> viewModel.requestRequirement()
                ASK_FOR_REVIEW_SUCCESSFULLY -> toast(getString(R.string.ask_for_review_successful))
                NOT_APPROVED_MASTER -> {
                    toast(getString(R.string.not_approved_master))
                    onBackPressed()
                }
                REQUEST_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    private fun showDialogForCallingCustomer(requirement: Requirement) {
        when (requirement.status) {
            // NOTE: 매칭대기 상태에서, 전화기능이 오픈되었다는 것을 안내
            is RequirementStatus.Estimated -> {
                DefaultDialog.newInstance(DialogData.getNoticeForCallingCustomerInViewRequirement())
                    .let {
                        it.setButtonsClickListener(
                            onPositive = { viewModel.callToClient() },
                            onNegative = {}
                        )
                        it.show(supportFragmentManager, it.tag)
                    }
            }

            // NOTE: 상담요청 상태에서, 상호 통화한 적이 한번도 없으면 전화하라고 안내
            is RequirementStatus.RequestConsult -> {
                if (requirement.estimationDto?.fromMasterCallCnt == 0 && requirement.estimationDto.fromClientCallCnt == 0) {
                    DefaultDialog.newInstance(DialogData.getRequestConsultAlertDialogData())
                        .let {
                            it.setButtonsClickListener(
                                onPositive = {},
                                onNegative = {}
                            )
                            it.show(supportFragmentManager, it.tag)
                        }
                }
            }

            // NOTE: 실측예정 상태에서, 상호 통화한 적이 한번도 없으면 전화하라고 안내
            is RequirementStatus.Measuring -> {
                if (requirement.estimationDto?.fromMasterCallCnt == 0 && requirement.estimationDto.fromClientCallCnt == 0) {
                    DefaultDialog.newInstance(DialogData.getRecommendingCallingCustomer())
                        .let {
                            it.setButtonsClickListener(
                                onPositive = { viewModel.callToClient() },
                                onNegative = {}
                            )
                            it.show(supportFragmentManager, it.tag)
                        }
                }
            }
            else -> Unit
        }
    }

    private fun isValidRequirement(requirement: Requirement): Boolean {
        if (requirement.estimationDto?.masterResponseCode == EstimationResponseCode.EXPIRED) {
            DefaultDialog.newInstance(
                dialogData = getExpiredRequestConsultDialogData(),
                cancelable = false
            ).let {
                it.setButtonsClickListener(
                    onPositive = { onBackPressed() },
                    onNegative = {}
                )
                it.show(supportFragmentManager, it.tag)
            }
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestMasterSimpleInfo()
        viewModel.requestRequirement()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Note: ViewRequirement 화면에서 notification 으로 해당 requirement 에 접근했을 때, 화면을 refresh 해주기 위함
        intent?.let {
            viewModel.requirementId.value = ViewRequirementActivityHelper.getRequirementId(it)
            viewModel.requestRequirement()
        }
    }

    companion object {
        private const val TAG = "ViewEstimateActivity"
    }
}
