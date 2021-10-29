package kr.co.soogong.master.ui.dialog.popup

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import kr.co.soogong.master.databinding.DialogCustomBinding
import timber.log.Timber

class CustomDialog() : DialogFragment() {
    lateinit var binding: DialogCustomBinding

    private val dialogData: DialogData? by lazy {
        arguments?.getParcelable(DIALOG_DATA)
    }

    private lateinit var customDialogClickListener: CustomDialogClickListener

    interface CustomDialogClickListener {
        fun onPositiveClicked()
        fun onNegativeClicked()
    }

    fun setButtonsClickListener(onPositive: () -> Unit, onNegative: () -> Unit) {
        this.customDialogClickListener = object : CustomDialogClickListener {
            override fun onPositiveClicked() {
                onPositive()
            }

            override fun onNegativeClicked() {
                onNegative()
            }
        }
    }

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

        binding = DialogCustomBinding.inflate(inflater, container, false)

        // 다이얼로그 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        dialogData?.let { dialogData ->
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

                if (dialogData.negativeBtnText.isNullOrEmpty()) {
                    btnNo.isVisible = false
                    line.isVisible = false
                } else {
                    btnNo.text = dialogData.negativeBtnText
                    btnNo.setTextColor(dialogData.negativeBtnTextColor)
                    btnNo.setOnClickListener {
                        if (::customDialogClickListener.isInitialized) customDialogClickListener.onNegativeClicked()
                        dismiss()
                    }
                }

                if (dialogData.positiveBtnText.isNullOrEmpty()) {
                    btnYes.isVisible = false
                    line.isVisible = false
                } else {
                    btnYes.text = dialogData.positiveBtnText
                    btnYes.setTextColor(dialogData.positiveBtnTextColor)
                    btnYes.setOnClickListener {
                        if (::customDialogClickListener.isInitialized) customDialogClickListener.onPositiveClicked()
                        dismiss()
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "CustomDialog"
        private const val DIALOG_DATA = "DIALOG_DATA"

        fun newInstance(
            dialogData: DialogData,
            cancelable: Boolean = true,
        ) = CustomDialog().apply {
            arguments = Bundle().apply {
                putParcelable(DIALOG_DATA, dialogData)
            }
            isCancelable = cancelable
        }
    }
}