package kr.co.soogong.master.ui.dialog.loading

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import kr.co.soogong.master.databinding.DialogLoadingBinding
import timber.log.Timber

class LoadingDialog : DialogFragment() {
    lateinit var binding: DialogLoadingBinding

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
        binding = DialogLoadingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        super.onCreateDialog(savedInstanceState).apply {
            Timber.tag(TAG).d("onCreateDialog: ")

            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isCancelable = false
        }

    companion object {
        private const val TAG = "LoadingDialog"

        fun newInstance() = LoadingDialog()
    }
}