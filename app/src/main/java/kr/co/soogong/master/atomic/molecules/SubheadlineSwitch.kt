package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.databinding.ViewSubheadlineSwitchBinding

class SubheadlineSwitch @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewSubheadlineSwitchBinding.inflate(LayoutInflater.from(context), this, true)

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = it }
        }

    var content: String? = null
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                binding.tvContent.isVisible = true
                binding.tvContent.text = value
            }
        }

    var checked: Boolean = false
        set(value) {
            field = value
            binding.scSwitch.isChecked = value
        }
        get() {
            field = binding.scSwitch.isChecked
            return field
        }

    fun setSwitchClick(listener: CompoundButton.OnCheckedChangeListener) {
        binding.scSwitch.setOnCheckedChangeListener(listener)
    }
}