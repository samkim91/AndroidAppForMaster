package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTextCheckboxBinding
import kr.co.soogong.master.databinding.ViewTitleSwitchcompatBinding

class TextCheckBox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewTextCheckboxBinding.inflate(LayoutInflater.from(context), this, true)

    var text: String? = ""
        set(value) {
            field = value
            binding.text.text = value
        }

    val checkBox: CheckBox
        get() = binding.checkbox

    var lineVisible: Boolean = false
        set(value) {
            field = value
            binding.line.visibility = if (value) View.VISIBLE else View.GONE
        }

    fun setCheckClick(onClick: () -> Unit) {
        binding.checkbox.setOnClickListener { onClick() }
    }
}