package kr.co.soogong.master.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R

class SettingsButton : ConstraintLayout {
    private lateinit var textView: TextView

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
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SettingsButton)
        setTypeArray(typedArray)
    }

    private fun initView() {
        val infService = Context.LAYOUT_INFLATER_SERVICE;
        val layoutInflater = context.getSystemService(infService) as LayoutInflater
        val v = layoutInflater.inflate(R.layout.view_settings_button, this, false);
        addView(v)

        textView = findViewById(R.id.text)
    }

    private fun getAttrs(attributeSet: AttributeSet, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.SettingsButton, defStyle, 0)
        setTypeArray(typedArray)
    }


    private fun setTypeArray(typedArray: TypedArray) {
        val textString = typedArray.getString(R.styleable.SettingsButton_text)
        textView.text = textString
        typedArray.recycle()
    }

}