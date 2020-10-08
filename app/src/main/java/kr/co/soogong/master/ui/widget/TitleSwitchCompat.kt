package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewTitleSwitchcompatBinding

class TitleSwitchCompat @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding: ViewTitleSwitchcompatBinding =
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

    fun setSwitchClick(lister: CompoundButton.OnCheckedChangeListener) {
        binding.switchCompat.setOnCheckedChangeListener(lister)
    }
}