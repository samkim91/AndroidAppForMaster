package kr.co.soogong.master.ui.dialog.bottomDialogCountableEdittext

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.databinding.BottomDialogCountableEdittextBinding
import kr.co.soogong.master.ui.base.BaseBottomSheetDialogFragment
import timber.log.Timber

class BottomDialogCountableEdittext :
    BaseBottomSheetDialogFragment<BottomDialogCountableEdittextBinding>(
        R.layout.bottom_dialog_countable_edittext
    ) {

    private val estimationTemplate: EstimationTemplateDto? by lazy {
        arguments?.getParcelable(ESTIMATION_TEMPLATE)
    }

    @Suppress("UNCHECKED_CAST")
    private val closeClick: (EstimationTemplateDto) -> Unit by lazy {
        arguments?.get(CLOSE_CLICK) as (EstimationTemplateDto) -> Unit
    }

    @Suppress("UNCHECKED_CAST")
    private val confirmClick: (EstimationTemplateDto) -> Unit by lazy {
        arguments?.get(CONFIRM_CLICK) as (EstimationTemplateDto) -> Unit
    }

    @Suppress("UNCHECKED_CAST")
    private val cancelListener: (EstimationTemplateDto) -> Unit by lazy {
        arguments?.get(CANCEL_LISTENER) as (EstimationTemplateDto) -> Unit
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // keyboard 를 열었을 때, dialog 를 전체 화면으로 올려주기 위함
        return super.onCreateDialog(savedInstanceState).apply {
            if (this is BottomSheetDialog) {
                behavior.skipCollapsed = true
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
    }

    override fun initLayout() {
        with(binding) {
            edittextDescription.text = estimationTemplate?.description

            closeButton.setOnClickListener {
                closeClick(
                    EstimationTemplateDto(
                        id = estimationTemplate?.id ?: 0,
                        masterId = estimationTemplate?.masterId,
                        description = edittextDescription.text!!,
                    )
                )
                dismiss()
            }

            confirmButton.setOnClickListener {
                confirmClick(
                    EstimationTemplateDto(
                        id = estimationTemplate?.id ?: 0,
                        masterId = estimationTemplate?.masterId,
                        description = edittextDescription.text!!,
                    )
                )
                dismiss()
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        cancelListener(
            EstimationTemplateDto(
                id = estimationTemplate?.id ?: 0,
                masterId = estimationTemplate?.masterId,
                description = binding.edittextDescription.text!!,
            )
        )
    }

    companion object {
        private const val TAG = "BottomDialogCountableEdittext"
        private const val ESTIMATION_TEMPLATE = "ESTIMATION_TEMPLATE"
        private const val CLOSE_CLICK = "CLOSE_CLICK"
        private const val CONFIRM_CLICK = "CONFIRM_CLICK"
        private const val CANCEL_LISTENER = "CANCEL_LISTENER"

        fun newInstance(
            estimationTemplateDto: EstimationTemplateDto?,
            closeClick: (EstimationTemplateDto) -> Unit,
            confirmClick: (EstimationTemplateDto) -> Unit,
            cancelListener: (EstimationTemplateDto) -> Unit,
        ) = BottomDialogCountableEdittext().apply {
            arguments = bundleOf(
                ESTIMATION_TEMPLATE to estimationTemplateDto,
                CLOSE_CLICK to closeClick,
                CONFIRM_CLICK to confirmClick,
                CANCEL_LISTENER to cancelListener
            )
        }
    }
}