package kr.co.soogong.master.ui.dialog.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.soogong.master.databinding.BottomSheetDialogCustomBinding
import kr.co.soogong.master.generated.callback.OnClickListener
import timber.log.Timber

class CustomBottomSheetDialog(
    private val title: String,
    private val dialogData: List<BottomSheetDialogData>,
    private var itemClick: (String) -> Unit
) : BottomSheetDialogFragment() {
    lateinit var binding: BottomSheetDialogCustomBinding

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

        binding = BottomSheetDialogCustomBinding.inflate(inflater, container, false)

        (dialog as? BottomSheetDialog)?.let {
            it.behavior.isDraggable = false
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        binding.dialogTitle.text = title

        binding.bottomSheetDialogRecyclerview.adapter = BottomSheetDialogAdapter(itemClickListener = {
            Timber.tag(TAG).w(" $it is clicked")
            itemClick(it)
            dismiss()
        })

        (binding.bottomSheetDialogRecyclerview.adapter as? BottomSheetDialogAdapter)?.submitList(dialogData)
    }


    companion object{
        private const val TAG = "CustomBottomSheetDialog"
    }
}