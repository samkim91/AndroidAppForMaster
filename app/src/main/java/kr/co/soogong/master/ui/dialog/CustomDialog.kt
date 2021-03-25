package kr.co.soogong.master.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kr.co.soogong.master.databinding.DialogCustomBinding

class CustomDialog(
    private val dialogData: DialogData,
    private var yesClick: (() -> Unit),
    private var noClick: (() -> Unit),
) : DialogFragment() {
    lateinit var binding: DialogCustomBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCustomBinding.inflate(inflater, container, false)
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
                yesClick()
            }

            btnYes.text = dialogData.positiveBtnText
            btnYes.setTextColor(dialogData.positiveBtnTextColor)
            btnYes.setOnClickListener {
                noClick()
                dismiss()
            }
        }
        return binding.root
    }
}