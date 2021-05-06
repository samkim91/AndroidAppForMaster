package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewTitleSwitchcompatBinding

class TitleSwitchCompat @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewTitleSwitchcompatBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var checked: Boolean = false
        set(value) {
            field = value
            binding.switchCompat.isChecked = value
        }
        get() {
            field = binding.switchCompat.isChecked
            return field
        }

    var underLine: Boolean = true
        set(value) {
            field = value
            binding.line.visibility = if (value) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    fun setSwitchClick(listener: CompoundButton.OnCheckedChangeListener) {
        binding.switchCompat.setOnCheckedChangeListener(listener)
    }
}