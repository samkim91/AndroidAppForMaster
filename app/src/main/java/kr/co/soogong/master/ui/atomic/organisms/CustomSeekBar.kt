package kr.co.soogong.master.ui.atomic.organisms

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import kr.co.soogong.master.R

class CustomSeekBar : AppCompatSeekBar {
    private val maxValue = 5

    private var mSeekBarHintPaint: Paint? = null
    private var mHintTextColor: Int = 0
    private var mHintTextSize: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SeekbarHint,
            0, 0
        )

        try {
            mHintTextColor = a.getColor(R.styleable.SeekbarHint_hint_text_color, 0)
            mHintTextSize = a.getDimension(R.styleable.SeekbarHint_hint_text_size, 0f)
        } finally {
            a.recycle()
        }

        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        max = maxValue
        mSeekBarHintPaint = TextPaint()
        mSeekBarHintPaint!!.color = mHintTextColor
        mSeekBarHintPaint!!.textSize = mHintTextSize
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        val labelX = thumb.bounds.centerX() - (mHintTextSize / 1)
//        val labelY = thumb.bounds.centerY().toFloat() // + (mHintTextSize / 4)
//        canvas.drawText(
//            "$progress%", if (progress > 90) labelX - (mHintTextSize / 2) else labelX,
//            labelY, mSeekBarHintPaint!!
//        )
    }
}