package kr.co.soogong.master.ui.requirement.action.write.template

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.databinding.ActivityEstimationTemplatesBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.bottomDialogCountableEdittext.BottomDialogCountableEdittext
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.requirement.action.write.template.EstimationTemplatesViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.uihelper.requirment.action.EstimationTemplatesActivityHelper
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

            with(actionBar) {
                title.text = getString(R.string.estimation_template_action_bar_text)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
                button.text = getString(R.string.add_button_text)
                button.setOnClickListener {
                    showBottomSheetDialog(null)
                }
            }

            recyclerviewForTemplates.adapter = EstimationTemplateAdapter(
                leftButtonClick = { template ->
                    Timber.tag(TAG).d("leftButtonClick: $template")
                    CustomDialog.newInstance(
                        dialogData = DialogData.getConfirmingForDeletingEstimationTemplate(this@EstimationTemplatesActivity),
                        yesClick = {
                            viewModel.estimationTemplate.value = template
                            viewModel.deleteEstimationTemplate()
                        },
                        noClick = {}
                    ).run {
                        show(supportFragmentManager, tag)
                    }
                },
                middleButtonClick = { template ->
                    Timber.tag(TAG).d("middleButtonClick: $template")
                    showBottomSheetDialog(
                        EstimationTemplateDto(id = template.id,
                            masterId = template.masterId,
                            description = template.description)
                    )
                },
                rightButtonClick = { template ->
                    Timber.tag(TAG).d("rightButtonClick: $template")
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
        BottomDialogCountableEdittext.newInstance(
            estimationTemplateDto = estimationTemplateDto,
            closeClick = {
                viewModel.estimationTemplate.value = it
            },
            confirmClick = {
                viewModel.estimationTemplate.value = it
                viewModel.saveEstimationTemplate()
            },
            cancelListener = {
                if (it.description.isNotEmpty()) CustomDialog.newInstance(
                    dialogData = DialogData.getConfirmingForIgnoreChangeOfEstimationTemplate(this),
                    yesClick = { showBottomSheetDialog(it) },
                    noClick = { }
                ).run {
                    show(supportFragmentManager, tag)
                }
            }
        ).run {
            show(supportFragmentManager, tag)
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
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