package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.BottomDialogRecyclerViewBinding
import kr.co.soogong.master.ui.base.BaseBottomSheetDialogFragment
import timber.log.Timber
import java.io.Serializable

class BottomDialogRecyclerView : BaseBottomSheetDialogFragment<BottomDialogRecyclerViewBinding>(
    R.layout.bottom_dialog_recycler_view
) {

    private val dialogBundle: BottomDialogBundle? by lazy {
        arguments?.getParcelable(DIALOG_DATA)
    }

    @Suppress("UNCHECKED_CAST")
    private val itemClick: (String, Int) -> Unit by lazy {
        arguments?.getSerializable(ITEM_CLICK) as (String, Int) -> Unit
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
    }

    override fun initLayout() {
        (dialog as? BottomSheetDialog)?.let {
            it.behavior.isDraggable = false
        }

        with(binding) {
            dialogBundle?.let {
                if (it.title.isNotEmpty()) {
                    dialogTitle.text = it.title
                    dialogTitle.isVisible = true
                    dialogSubtitle.gravity = Gravity.START
                    dialogSubtitle.setTextAppearance(R.style.text_style_14sp_regular)
                }

                dialogSubtitle.text = it.subtitle

                bottomSheetDialogRecyclerview.adapter =
                    BottomDialogAdapter(itemClickListener = { text, value ->
                        Timber.tag(TAG).w(" $text is clicked / value is $value")
                        itemClick(text, value)
                        dismiss()
                    })

                (bottomSheetDialogRecyclerview.adapter as? BottomDialogAdapter)?.submitList(it.list)
            }
        }
    }

    companion object {
        private const val TAG = "BottomDialogRecyclerView"
        private const val DIALOG_DATA = "DIALOG_DATA"
        private const val ITEM_CLICK = "ITEM_CLICK"

        fun newInstance(
            dialogBundle: BottomDialogBundle,
            itemClick: (String, Int) -> Unit,
        ): BottomDialogRecyclerView {
            val args = Bundle()
            args.putParcelable(DIALOG_DATA, dialogBundle)
            args.putSerializable(ITEM_CLICK, itemClick as Serializable)

            return BottomDialogRecyclerView().apply { arguments = args }
        }
    }
}