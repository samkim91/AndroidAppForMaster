package kr.co.soogong.master.ui.dialog.popup

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import kr.co.soogong.master.databinding.DialogDefaultBinding
import timber.log.Timber

class DefaultDialog() : DialogFragment() {
    private lateinit var binding: DialogDefaultBinding

    private val dialogData: DialogData? by lazy {
        arguments?.getParcelable(DIALOG_DATA)
    }

    private lateinit var defaultDialogClickListener: DefaultDialogClickListener

    interface DefaultDialogClickListener {
        fun onPositiveClicked()
        fun onNegativeClicked()
    }

    fun setButtonsClickListener(onPositive: () -> Unit, onNegative: () -> Unit) {
        this.defaultDialogClickListener = object : DefaultDialogClickListener {
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

        binding = DialogDefaultBinding.inflate(inflater, container, false)

        // 다이얼로그 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        dialogData?.let { dialogData ->
            with(binding) {
                tvTitle.text = dialogData.title

                if (dialogData.content.isEmpty()) {
                    tvContent.isVisible = false
                } else {
                    tvContent.isVisible = true
                    tvContent.text = dialogData.content
                }

                if (dialogData.alert.isEmpty()) {
                    tvAlert.isVisible = false
                } else {
                    tvAlert.isVisible = true
                    tvAlert.text = dialogData.alert
                }

                if (dialogData.negativeText.isEmpty()) {
                    bNegative.isVisible = false
                } else {
                    bNegative.text = dialogData.negativeText
                    bNegative.setOnClickListener {
                        if (::defaultDialogClickListener.isInitialized) defaultDialogClickListener.onNegativeClicked()
                        dismiss()
                    }
                }

                if (dialogData.positiveText.isEmpty()) {
                    bPositive.isVisible = false
                } else {
                    bPositive.text = dialogData.positiveText
                    bPositive.setOnClickListener {
                        if (::defaultDialogClickListener.isInitialized) defaultDialogClickListener.onPositiveClicked()
                        dismiss()
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "DefaultDialog"
        private const val DIALOG_DATA = "DIALOG_DATA"

        fun newInstance(
            dialogData: DialogData,
            cancelable: Boolean = true,
        ) = DefaultDialog().apply {
            arguments = Bundle().apply {
                putParcelable(DIALOG_DATA, dialogData)
            }
            isCancelable = cancelable
        }
    }
}