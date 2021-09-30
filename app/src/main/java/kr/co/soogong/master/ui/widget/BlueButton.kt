package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewBlueButtonBinding

class BlueButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewBlueButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var icon: Int = Int.MIN_VALUE
        set(value) {
            field = value
            binding.icon.setImageResource(value)
        }

    var buttonText: String? = ""
        set(value) {
            field = value
            binding.buttonText.text = value
        }
}