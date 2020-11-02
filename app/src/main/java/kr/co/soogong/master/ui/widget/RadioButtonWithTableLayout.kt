package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout

class RadioButtonWithTableLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle), View.OnClickListener {
    var btnCurrentRadio: RadioButton? = null

    override fun onClick(v: View) {
        val btnRadio = v as RadioButton

        btnCurrentRadio?.isChecked = false
        btnRadio.isChecked = true
        btnCurrentRadio = btnRadio
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        setChildrenOnClickListener(child)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        super.addView(child, params)
        setChildrenOnClickListener(child)
    }

    private fun setChildrenOnClickListener(v: View) {
        (v as? RadioButton)?.setOnClickListener(this)
    }
}