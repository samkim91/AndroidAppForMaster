package kr.co.soogong.master.presentation.ui.requirement.action.write.template

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.entity.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.databinding.ActivityEstimationTemplatesBinding
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.common.dialog.bottomDialogCountableEdittext.BottomSheetDialogEstimationTemplate
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.requirement.action.write.template.EstimationTemplatesViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.uihelper.requirment.action.EstimationTemplatesActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EstimationTemplatesActivity : BaseActivity<ActivityEstimationTemplatesBinding>(
    R.layout.activity_estimation_templates
) {
    private val viewModel: EstimationTemplatesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            vm = viewModel
            lifecycleOwner = this@EstimationTemplatesActivity

            abHeader.setIvBackClickListener { onBackPressed() }

            recyclerviewForTemplates.adapter = EstimationTemplateAdapter(
                onDeletingClicked = { template ->
                    Timber.tag(TAG).d("buttonLeftClick: $template")
                    DefaultDialog.newInstance(
                        DialogData.getConfirmingForDeletingEstimationTemplate()
                    ).let {
                        it.setButtonsClickListener(
                            onPositive = {
                                viewModel.estimationTemplate.value = template
                                viewModel.deleteEstimationTemplate()
                            },
                            onNegative = {}
                        )
                        it.show(supportFragmentManager, it.tag)
                    }
                },
                onEditingClicked = { template ->
                    Timber.tag(TAG).d("middleButtonClick: $template")
                    showBottomSheetDialog(
                        EstimationTemplateDto(id = template.id,
                            masterId = template.masterId,
                            description = template.description)
                    )
                },
                onApplyingClicked = { template ->
                    Timber.tag(TAG).d("buttonRightClick: $template")
                    this@EstimationTemplatesActivity.run {
                        setResult(
                            RESULT_OK,
                            EstimationTemplatesActivityHelper.setResponse(template.description)
                        )
                        finish()
                    }
                }
            )
        }
    }

    private fun showBottomSheetDialog(estimationTemplateDto: EstimationTemplateDto?) {
        BottomSheetDialogEstimationTemplate.newInstance(
            estimationTemplateDto = estimationTemplateDto
        ).let {
            it.setButtonsClickListener(
                onClose = { templateDto ->
                    viewModel.estimationTemplate.value = templateDto
                },
                onConfirm = { templateDto ->
                    if (templateDto.description.isNotEmpty()) {
                        viewModel.estimationTemplate.value = templateDto
                        viewModel.saveEstimationTemplate()
                    }
                },
                onCancel = { templateDto ->
                    if (templateDto.description.isNotEmpty()) DefaultDialog.newInstance(
                        DialogData.getConfirmingForIgnoreChangeOfEstimationTemplate()
                    ).let { dialog ->
                        dialog.setButtonsClickListener(
                            onPositive = { showBottomSheetDialog(templateDto) },
                            onNegative = { }
                        )
                        dialog.show(supportFragmentManager, dialog.tag)
                    }
                }
            )
            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                EstimationTemplatesViewModel.START_ADDING_TEMPLATE -> showBottomSheetDialog(null)
                REQUEST_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestEstimationTemplates()
    }

    companion object {
        private const val TAG = "EstimationTemplatesActivity"

    }
}