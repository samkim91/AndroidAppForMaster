package kr.co.soogong.master.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kr.co.soogong.master.databinding.DialogCustomBinding
import timber.log.Timber

class CustomDialog(
    private val dialogData: DialogData,
    private var yesClick: (() -> Unit),
    private var noClick: (() -> Unit),
) : DialogFragment() {
    lateinit var binding: DialogCustomBinding

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

        binding = DialogCustomBinding.inflate(inflater, container, false)

        // 다이얼로그 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        with(binding) {
            txtDialogContent.text = dialogData.title
            txtDialogContent.setTextColor(dialogData.titleTxtColor)

            if (dialogData.description == null) {
                txtDialogAlert.visibility = View.GONE
            } else {
                txtDialogAlert.visibility = View.VISIBLE
                txtDialogAlert.text = dialogData.description
                txtDialogAlert.setTextColor(dialogData.descriptionTxtColor)
            }

            btnNo.text = dialogData.negativeBtnText
            btnNo.setTextColor(dialogData.negativeBtnTextColor)
            btnNo.setOnClickListener {
                noClick()
                dismiss()
            }

            btnYes.text = dialogData.positiveBtnText
            btnYes.setTextColor(dialogData.positiveBtnTextColor)
            btnYes.setOnClickListener {
                yesClick()
            }
        }


    }

    companion object{
        private const val TAG = "CustomDialog"
    }
}