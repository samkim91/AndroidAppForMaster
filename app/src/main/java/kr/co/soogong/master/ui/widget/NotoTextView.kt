package kr.co.soogong.master.ui.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import kr.co.soogong.master.R

class NotoTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        setType(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        setType(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr) {
        setType(context, attributeSet)
    }

    private fun setType(context: Context) {
        this.typeface = Typeface.createFromAsset(context.assets, "NotoSans-Regular.ttf")
    }

    private fun setType(context: Context, attributeSet: AttributeSet) {
        val typedArray =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.NotoTextView, 0, 0)

        when (typedArray.getInteger(R.styleable.NotoTextView_fontweight, 0)) {
            0 -> {
                this.typeface =
                    Typeface.createFromAsset(context.assets, "NotoSans-Regular.ttf")
            }
            1 -> {
                this.typeface =
                    Typeface.createFromAsset(context.assets, "NotoSans-Medium.ttf")
            }
            2 -> {
                this.typeface =
                    Typeface.createFromAsset(context.assets, "NotoSans-Bold.ttf")
            }
        }
    }
}