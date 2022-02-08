package kr.co.soogong.master.presentation.ui.common.dialog.bottomSheetDialogRecyclerView

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.BottomSheetDialogRecyclerViewBinding
import kr.co.soogong.master.presentation.ui.base.BaseBottomSheetDialogFragment
import timber.log.Timber

class BottomSheetDialogRecyclerView :
    BaseBottomSheetDialogFragment<BottomSheetDialogRecyclerViewBinding>(
        R.layout.bottom_sheet_dialog_recycler_view
    ) {

    private val bottomSheetDialogBundle: BottomSheetDialogBundle? by lazy {
        arguments?.getParcelable(DIALOG_DATA)
    }

    // 클릭 리스너를 설정하는 곳
    private lateinit var bottomSheetDialogItemClickListener: BottomSheetDialogItemClickListener

    interface BottomSheetDialogItemClickListener {
        fun onItemClick(bottomSheetDialogItem: BottomSheetDialogItem)
    }

    fun setItemClickListener(onClick: (BottomSheetDialogItem) -> Unit) {
        this.bottomSheetDialogItemClickListener = object : BottomSheetDialogItemClickListener {
            override fun onItemClick(bottomSheetDialogItem: BottomSheetDialogItem) {
                onClick(bottomSheetDialogItem)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 다이얼로그를 화면 크기에 맞춰서 확장하는 코드
        return super.onCreateDialog(savedInstanceState).also { dialog ->
            (dialog as BottomSheetDialog).behavior.state = STATE_EXPANDED
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        // 내부에 recyclerView 가 있어서, 드래그 기능을 제거
        (dialog as? BottomSheetDialog)?.let {
            it.behavior.isDraggable = false
        }

        initLayout()
    }

    override fun initLayout() {
        with(binding) {
            bottomSheetDialogBundle?.let { dialogBundle ->
                tvTitle.text = dialogBundle.title
                tvTitle.isVisible = dialogBundle.title.isNotEmpty()
                tvSubtitle.text = dialogBundle.subtitle
                tvSubtitle.isVisible = dialogBundle.subtitle.isNotEmpty()

                bottomSheetDialogRecyclerview.adapter =
                    BottomSheetDialogAdapter(itemClickListener = { item ->
                        if (::bottomSheetDialogItemClickListener.isInitialized) bottomSheetDialogItemClickListener.onItemClick(
                            item)
                        dismiss()
                    })

                (bottomSheetDialogRecyclerview.adapter as? BottomSheetDialogAdapter)?.submitList(
                    dialogBundle.list)
            }
        }
    }

    companion object {
        private const val TAG = "BottomDialogRecyclerView"
        private const val DIALOG_DATA = "DIALOG_DATA"

        fun newInstance(sheetDialogBundle: BottomSheetDialogBundle) =
            BottomSheetDialogRecyclerView().apply {
                arguments = Bundle().apply {
                    putParcelable(DIALOG_DATA, sheetDialogBundle)
                }
            }
    }
}