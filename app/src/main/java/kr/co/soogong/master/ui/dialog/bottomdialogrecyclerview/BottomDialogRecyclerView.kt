package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.soogong.master.databinding.BottomDialogRecyclerViewBinding
import timber.log.Timber
import java.io.Serializable

class BottomDialogRecyclerView() : BottomSheetDialogFragment() {
    lateinit var binding: BottomDialogRecyclerViewBinding

    private val titleTest: String? by lazy {
        arguments?.getString(TITLE)
    }

    @Suppress("UNCHECKED_CAST")
    private val dialogDataTest: List<BottomDialogData>? by lazy {
        arguments?.getSerializable(DIALOG_DATA) as List<BottomDialogData>
    }

    @Suppress("UNCHECKED_CAST")
    private val itemClickTest: (String, Int) -> Unit by lazy {
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

        binding.dialogTitle.text = titleTest

        binding.bottomSheetDialogRecyclerview.adapter = BottomDialogAdapter(itemClickListener = { text, value ->
            Timber.tag(TAG).w(" $text is clicked / value is $value")
            itemClickTest(text, value)
            dismiss()
        })

        (binding.bottomSheetDialogRecyclerview.adapter as? BottomDialogAdapter)?.submitList(dialogDataTest)
    }


    companion object{
        private const val TAG = "CustomBottomSheetDialog"
        private const val TITLE = "TITLE"
        private const val DIALOG_DATA = "DIALOG_DATA"
        private const val ITEM_CLICK = "ITEM_CLICK"

        fun newInstance(title: String, dialogData: List<BottomDialogData>, itemClick: (String, Int) -> Unit): BottomDialogRecyclerView {
            val args = Bundle()
            args.putString(TITLE, title)
            args.putSerializable(DIALOG_DATA, dialogData as Serializable)
            args.putSerializable(ITEM_CLICK, itemClick as Serializable)

            return BottomDialogRecyclerView().apply { arguments = args }
        }
    }
}