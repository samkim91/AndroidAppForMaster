package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.soogong.master.databinding.BottomDialogRecyclerViewBinding
import timber.log.Timber

class BottomDialogRecyclerView(
    private val title: String,
    private val dialogData: List<BottomDialogData>,
    private var itemClick: (String, Int) -> Unit
) : BottomSheetDialogFragment() {
    lateinit var binding: BottomDialogRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        binding.dialogTitle.text = title

        binding.bottomSheetDialogRecyclerview.adapter = BottomDialogAdapter(itemClickListener = { text, value ->
            Timber.tag(TAG).w(" $text is clicked / value is $value")
            itemClick(text, value)
            dismiss()
        })

        (binding.bottomSheetDialogRecyclerview.adapter as? BottomDialogAdapter)?.submitList(dialogData)
    }


    companion object{
        private const val TAG = "CustomBottomSheetDialog"
    }
}