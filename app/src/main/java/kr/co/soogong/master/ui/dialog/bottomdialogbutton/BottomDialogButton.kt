package kr.co.soogong.master.ui.dialog.bottomdialogbutton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.soogong.master.databinding.BottomDialogButtonBinding
import timber.log.Timber

// TODO: 2021/10/07 아직 사용하지 않고 있어서 리팩토링을 안 했는데, BottomDialogRecyclerView 를 참고해서 상속으로 변경해야함.
class BottomDialogButton(
    private val dialogData: BottomDialogButtonData,
    private val leftButtonClick: () -> Unit,
    private var rightButtonClick: () -> Unit,
) : BottomSheetDialogFragment() {
    lateinit var binding: BottomDialogButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Timber.tag(TAG).d("onCreateView: ")

        binding = BottomDialogButtonBinding.inflate(inflater, container, false)

        (dialog as? BottomSheetDialog)?.let {
            it.behavior.isDraggable = false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        with(binding) {
            dialogTitle.text = dialogData.title
            leftButton.text = dialogData.leftButtonText
            leftButton.setTextColor(dialogData.leftButtonTextColor)
            leftButton.setOnClickListener {
                leftButtonClick()
                dismiss()
            }
            rightButton.text = dialogData.rightButtonText
            rightButton.setTextColor(dialogData.rightButtonTextColor)
            rightButton.setOnClickListener {
                rightButtonClick()
                dismiss()
            }
        }
    }

    companion object {
        private const val TAG = "CustomBottomSheetDialog"
    }
}