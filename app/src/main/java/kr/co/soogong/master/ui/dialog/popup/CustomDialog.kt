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
import java.io.Serializable

class CustomDialog() : DialogFragment() {
    lateinit var binding: DialogCustomBinding

    private val dialogData: DialogData? by lazy {
        arguments?.getParcelable(DIALOG_DATA)
    }

    @Suppress("UNCHECKED_CAST")
    private val yesClick: () -> Unit by lazy {
        arguments?.getSerializable(YES_CLICK) as () -> Unit
    }

    @Suppress("UNCHECKED_CAST")
    private val noClick: () -> Unit by lazy {
        arguments?.getSerializable(NO_CLICK) as () -> Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
                } else {
                    btnNo.text = dialogData.negativeBtnText
                    btnNo.setTextColor(dialogData.negativeBtnTextColor)
                    btnNo.setOnClickListener {
                        noClick()
                        dismiss()
                    }
                }

                if (dialogData.positiveBtnText.isNullOrEmpty()) {
                    btnYes.isVisible = false
                } else {
                    btnYes.text = dialogData.positiveBtnText
                    btnYes.setTextColor(dialogData.positiveBtnTextColor)
                    btnYes.setOnClickListener {
                        yesClick()
                        dismiss()
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "CustomDialog"
        private const val DIALOG_DATA = "DIALOG_DATA"
        private const val YES_CLICK = "YES_CLICK"
        private const val NO_CLICK = "NO_CLICK"

        fun newInstance(
            dialogData: DialogData,
            yesClick: () -> Unit,
            noClick: () -> Unit
        ): CustomDialog {
            val args = Bundle()
            args.putParcelable(DIALOG_DATA, dialogData)
            args.putSerializable(YES_CLICK, yesClick as Serializable)
            args.putSerializable(NO_CLICK, noClick as Serializable)

            return CustomDialog().apply { arguments = args }
        }
    }
}