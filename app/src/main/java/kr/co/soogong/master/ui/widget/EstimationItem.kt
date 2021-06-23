package kr.co.soogong.master.ui.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewTransmissionMessageBinding

class EstimationItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
            ViewTransmissionMessageBinding.inflate(LayoutInflater.from(context), this, true)

    var key: String? = ""
        set(value) {
            field = value
            binding.key.text = value
        }

    var value: String? = ""
        set(value) {
            field = value
            binding.value.text = value
        }

    var bold: Boolean? = false
        set(value) {
            field = value
            binding.key.setTypeface(binding.key.typeface, Typeface.BOLD)
            binding.value.setTypeface(binding.value.typeface, Typeface.BOLD)
        }
}