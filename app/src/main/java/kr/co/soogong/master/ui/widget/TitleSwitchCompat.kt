package kr.co.soogong.master.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTitleSwitchcompatBinding

class TitleSwitchCompat @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding: ViewTitleSwitchcompatBinding =
        ViewTitleSwitchcompatBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)
        getAttrs(attributeSet, defStyle)
    }

    private fun getAttrs(attributeSet: AttributeSet?, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.TitleSwitchCompat, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        binding.title.text =
            typedArray.getString(R.styleable.TitleSwitchCompat_title_switch_title_text)
        binding.switchCompat.isChecked =
            typedArray.getBoolean(R.styleable.TitleSwitchCompat_title_switch_state, false)
        typedArray.recycle()
    }

    fun setTitleText(text: CharSequence) {
        binding.title.text = text
    }

    fun setSwitchState(state: Boolean) {
        binding.switchCompat.isChecked = state
    }

    fun setSwitchClick(lister: CompoundButton.OnCheckedChangeListener) {
        binding.switchCompat.setOnCheckedChangeListener(lister)
    }
}