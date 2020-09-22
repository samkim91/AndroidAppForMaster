package kr.co.soogong.master.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTitleSwitchcompatBinding

class TitleSwitchCompat : ConstraintLayout {
    private lateinit var titleTextView: TextView
    private lateinit var switch: SwitchCompat
    private lateinit var binding: ViewTitleSwitchcompatBinding

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
        getAttrs(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet
    ) {
        initView()
        getAttrs(attributeSet, defStyle)
    }

    private fun getAttrs(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TitleSwitchCompat)
        setTypeArray(typedArray)
    }

    private fun initView() {
        val infService = Context.LAYOUT_INFLATER_SERVICE;
        val layoutInflater = context.getSystemService(infService) as LayoutInflater
        binding = ViewTitleSwitchcompatBinding.inflate(layoutInflater, this, false)
        addView(binding.root)

        titleTextView = findViewById(R.id.title)
        switch = findViewById(R.id.switch_compat)
    }

    private fun getAttrs(attributeSet: AttributeSet, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.TitleSwitchCompat, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        titleTextView.text =
            typedArray.getString(R.styleable.TitleSwitchCompat_title_switch_title_text)
        switch.isChecked =
            typedArray.getBoolean(R.styleable.TitleSwitchCompat_title_switch_state, false)
        typedArray.recycle()
    }

    fun setTitleText(text: CharSequence) {
        titleTextView.text = text
    }

    fun setSwitchState(state: Boolean) {
        switch.isChecked = state
    }

    fun getSwitchState(): Boolean {
        return switch.isChecked
    }

    fun setSwitchClick(lister: CompoundButton.OnCheckedChangeListener) {
        switch.setOnCheckedChangeListener(lister)
    }
}