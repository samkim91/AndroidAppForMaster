package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.BottomDialogRecyclerViewBinding
import timber.log.Timber
import java.io.Serializable

class BottomDialogRecyclerView() : BottomSheetDialogFragment() {
    lateinit var binding: BottomDialogRecyclerViewBinding

    private val dialogBundle: BottomDialogBundle? by lazy {
        arguments?.getParcelable(DIALOG_DATA)
    }

    @Suppress("UNCHECKED_CAST")
    private val itemClick: (String, Int) -> Unit by lazy {
        arguments?.getSerializable(ITEM_CLICK) as (String, Int) -> Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.tag(TAG).d("onCreateView: ")

        binding = BottomDialogRecyclerViewBinding.inflate(inflater, container, false)

        (dialog as? BottomSheetDialog)?.let {
            it.behavior.isDraggable = false
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        with(binding) {
            dialogBundle?.let {
                if(it.title.isNotEmpty()) {
                    dialogTitle.text = it.title
                    dialogTitle.isVisible = true
                    dialogSubtitle.gravity = Gravity.START
                    dialogSubtitle.setTextAppearance(R.style.text_style_14sp_regular)
                }

                dialogSubtitle.text = it.subtitle

                bottomSheetDialogRecyclerview.adapter = BottomDialogAdapter(itemClickListener = { text, value ->
                    Timber.tag(TAG).w(" $text is clicked / value is $value")
                    itemClick(text, value)
                    dismiss()
                })

                (bottomSheetDialogRecyclerview.adapter as? BottomDialogAdapter)?.submitList(it.list)

            }
        }
    }

    companion object{
        private const val TAG = "BottomSheetDialog"
        private const val DIALOG_DATA = "DIALOG_DATA"
        private const val ITEM_CLICK = "ITEM_CLICK"

        fun newInstance(dialogBundle: BottomDialogBundle, itemClick: (String, Int) -> Unit): BottomDialogRecyclerView {
            val args = Bundle()
            args.putParcelable(DIALOG_DATA, dialogBundle)
            args.putSerializable(ITEM_CLICK, itemClick as Serializable)

            return BottomDialogRecyclerView().apply { arguments = args }
        }
    }
}