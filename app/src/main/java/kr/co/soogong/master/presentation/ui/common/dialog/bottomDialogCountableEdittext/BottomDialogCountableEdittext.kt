package kr.co.soogong.master.presentation.ui.common.dialog.bottomDialogCountableEdittext

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.BottomDialogCountableEdittextBinding
import kr.co.soogong.master.presentation.ui.base.BaseBottomSheetDialogFragment
import timber.log.Timber

class BottomDialogCountableEdittext :
    BaseBottomSheetDialogFragment<BottomDialogCountableEdittextBinding>(
        R.layout.bottom_dialog_countable_edittext
    ) {

    private val bottomDialogData: BottomDialogData by lazy {
        arguments?.getParcelable(BOTTOM_DIALOG_DATA)!!
    }

    private val content: String by lazy {
        arguments?.getString(CONTENT) ?: ""
    }

    // 클릭 리스너 설정
    private lateinit var bottomDialogCountableEdittextClickListener: BottomDialogCountableEdittextClickListener

    interface BottomDialogCountableEdittextClickListener {
        fun onNegativeClicked(content: String)
        fun onPositiveClicked(content: String)
        fun onCanceled(content: String)
    }

    fun setButtonsClickListener(
        onNegative: (content: String) -> Unit,
        onPositive: (content: String) -> Unit,
        onCancel: (content: String) -> Unit,
    ) {
        bottomDialogCountableEdittextClickListener =
            object : BottomDialogCountableEdittextClickListener {
                override fun onNegativeClicked(content: String) {
                    onNegative(content)
                }

                override fun onPositiveClicked(content: String) {
                    onPositive(content)
                }

                override fun onCanceled(content: String) {
                    onCancel(content)
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
            // bottom dialog 의 기본 내용 set
            stcDescription.subheadline = getString(bottomDialogData.title)
            stcDescription.hint = bottomDialogData.hint?.let { getString(it) }
            stcDescription.maxCount = bottomDialogData.maxCount
            content = this@BottomDialogCountableEdittext.content

            setNegativeClickListener {
                if (::bottomDialogCountableEdittextClickListener.isInitialized)
                    bottomDialogCountableEdittextClickListener.onNegativeClicked(binding.stcDescription.textareaCounter.textInputEditText.text.toString())
                dismiss()
            }

            setPositiveClickListener {
                if (::bottomDialogCountableEdittextClickListener.isInitialized)
                    bottomDialogCountableEdittextClickListener.onPositiveClicked(binding.stcDescription.textareaCounter.textInputEditText.text.toString())
                dismiss()
            }
        }
    }

    // 다이얼로그가 그냥 취소될 때, 변경사항을 지울지 확인하기 위함
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        if (::bottomDialogCountableEdittextClickListener.isInitialized)
            bottomDialogCountableEdittextClickListener.onCanceled(binding.stcDescription.textareaCounter.textInputEditText.text.toString())
    }

    companion object {
        private val TAG = BottomDialogCountableEdittext::class.java.name

        private const val BOTTOM_DIALOG_DATA = "BOTTOM_DIALOG_DATA"
        private const val CONTENT = "CONTENT"

        fun newInstance(
            bottomDialogData: BottomDialogData,
            content: String?,
        ) = BottomDialogCountableEdittext().apply {
            arguments = bundleOf(
                BOTTOM_DIALOG_DATA to bottomDialogData,
                CONTENT to content
            )
        }
    }
}