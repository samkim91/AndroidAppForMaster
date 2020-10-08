package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TableRow
import androidx.constraintlayout.widget.ConstraintLayout


class RadioButtonWithTableLayout(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), View.OnClickListener {
    var btnCurrentRadio: RadioButton? = null

    override fun onClick(v: View) {
        val btnRadio = v as RadioButton

        btnCurrentRadio?.isChecked = false
        btnRadio.isChecked = true
        btnCurrentRadio = btnRadio
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        setChildrenOnClickListener(child as TableRow)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        super.addView(child, params)
        setChildrenOnClickListener(child as TableRow)
    }

    private fun setChildrenOnClickListener(tr: TableRow) {
        val c: Int = tr.childCount
        for (i in 0 until c) {
            val v: View = tr.getChildAt(i)
            (v as? RadioButton)?.setOnClickListener(this)
        }
    }
}