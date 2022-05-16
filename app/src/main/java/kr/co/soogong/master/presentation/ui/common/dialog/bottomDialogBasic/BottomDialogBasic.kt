package kr.co.soogong.master.presentation.ui.common.dialog.bottomDialogBasic

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.BottomDialogBasicBinding
import kr.co.soogong.master.presentation.ui.base.BaseBottomSheetDialogFragment
import kr.co.soogong.master.presentation.ui.common.dialog.bottomDialogCountableEdittext.BottomDialogData
import timber.log.Timber

class BottomDialogBasic : BaseBottomSheetDialogFragment<BottomDialogBasicBinding>(
    R.layout.bottom_dialog_basic
) {

    private val bottomDialogData: BottomDialogData by lazy {
        arguments?.getParcelable(BOTTOM_DIALOG_DATA)!!
    }

    private val content: String by lazy {
        arguments?.getString(CONTENT) ?: ""
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
            content = this@BottomDialogBasic.content
        }
    }

    companion object {
        private val TAG = BottomDialogBasic::class.java.simpleName

        private const val BOTTOM_DIALOG_DATA = "BOTTOM_DIALOG_DATA"
        private const val CONTENT = "CONTENT"

        fun newInstance(
            bottomDialogData: BottomDialogData,
            content: String?,
        ) = BottomDialogBasic().apply {
            arguments = bundleOf(
                BOTTOM_DIALOG_DATA to bottomDialogData,
                CONTENT to content
            )
        }
    }
}