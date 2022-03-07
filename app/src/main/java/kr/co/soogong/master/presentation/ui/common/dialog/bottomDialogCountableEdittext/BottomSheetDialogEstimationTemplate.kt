package kr.co.soogong.master.presentation.ui.common.dialog.bottomDialogCountableEdittext

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.co.soogong.master.R
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationTemplateDto
import kr.co.soogong.master.databinding.BottomSheetDialogEstimationTemplateBinding
import kr.co.soogong.master.presentation.ui.base.BaseBottomSheetDialogFragment
import timber.log.Timber

class BottomSheetDialogEstimationTemplate :
    BaseBottomSheetDialogFragment<BottomSheetDialogEstimationTemplateBinding>(
        R.layout.bottom_sheet_dialog_estimation_template
    ) {

    private val estimationTemplate: EstimationTemplateDto? by lazy {
        arguments?.getParcelable(ESTIMATION_TEMPLATE)
    }

    // 클릭 리스너를 설정하는 곳
    private lateinit var bottomSheetDialogEstimationTemplateClickListener: BottomSheetDialogEstimationTemplateClickListener

    interface BottomSheetDialogEstimationTemplateClickListener {
        fun onCloseClick(estimationTemplateDto: EstimationTemplateDto)
        fun onConfirmClick(estimationTemplateDto: EstimationTemplateDto)
        fun onCancelClick(estimationTemplateDto: EstimationTemplateDto)
    }

    fun setButtonsClickListener(
        onClose: (EstimationTemplateDto) -> Unit,
        onConfirm: (EstimationTemplateDto) -> Unit,
        onCancel: (EstimationTemplateDto) -> Unit,
    ) {
        bottomSheetDialogEstimationTemplateClickListener =
            object : BottomSheetDialogEstimationTemplateClickListener {
                override fun onCloseClick(estimationTemplateDto: EstimationTemplateDto) {
                    onClose(estimationTemplateDto)
                }

                override fun onConfirmClick(estimationTemplateDto: EstimationTemplateDto) {
                    onConfirm(estimationTemplateDto)
                }

                override fun onCancelClick(estimationTemplateDto: EstimationTemplateDto) {
                    onCancel(estimationTemplateDto)
                }
            }
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
            estimationDescription = estimationTemplate?.description

            setCloseClickListener {
                if (::bottomSheetDialogEstimationTemplateClickListener.isInitialized) bottomSheetDialogEstimationTemplateClickListener.onCloseClick(
                    EstimationTemplateDto(
                        id = estimationTemplate?.id ?: 0,
                        masterId = estimationTemplate?.masterId,
                        description = binding.stcDescription.textareaCounter.textInputEditText.text.toString(),
                    )
                )
                dismiss()
            }

            setConfirmClickListener {
                if (::bottomSheetDialogEstimationTemplateClickListener.isInitialized) bottomSheetDialogEstimationTemplateClickListener.onConfirmClick(
                    EstimationTemplateDto(
                        id = estimationTemplate?.id ?: 0,
                        masterId = estimationTemplate?.masterId,
                        description = binding.stcDescription.textareaCounter.textInputEditText.text.toString()
                    )
                )
                dismiss()
            }
        }
    }

    // 다이얼로그가 그냥 취소될 때, 변경사항을 지울지 확인하기 위함
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        if (::bottomSheetDialogEstimationTemplateClickListener.isInitialized) bottomSheetDialogEstimationTemplateClickListener.onCancelClick(
            EstimationTemplateDto(
                id = estimationTemplate?.id ?: 0,
                masterId = estimationTemplate?.masterId,
                description = binding.stcDescription.textareaCounter.textInputEditText.text.toString(),
            )
        )
    }

    companion object {
        private const val TAG = "BottomDialogCountableEdittext"
        private const val ESTIMATION_TEMPLATE = "ESTIMATION_TEMPLATE"

        fun newInstance(
            estimationTemplateDto: EstimationTemplateDto?,
        ) = BottomSheetDialogEstimationTemplate().apply {
            arguments = bundleOf(
                ESTIMATION_TEMPLATE to estimationTemplateDto,
            )
        }
    }
}