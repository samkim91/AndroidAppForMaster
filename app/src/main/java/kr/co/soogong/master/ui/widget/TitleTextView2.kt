package kr.co.soogong.master.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R

class TitleTextView2 : ConstraintLayout {
    private lateinit var textView: TextView
    private lateinit var titleTextView: TextView

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
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TitleTextView2)
        setTypeArray(typedArray)
    }

    private fun initView() {
        val infService = Context.LAYOUT_INFLATER_SERVICE;
        val layoutInflater = context.getSystemService(infService) as LayoutInflater
        val v = layoutInflater.inflate(R.layout.view_title_textview2, this, false);
        addView(v)

        titleTextView = findViewById(R.id.title)
        textView = findViewById(R.id.text)
    }

    private fun getAttrs(attributeSet: AttributeSet, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.TitleTextView2, defStyle, 0)
        setTypeArray(typedArray)
    }


    private fun setTypeArray(typedArray: TypedArray) {
        titleTextView.text = typedArray.getString(R.styleable.TitleTextView2_title_text2)
        textView.text = typedArray.getString(R.styleable.TitleTextView2_detail_text2)
        typedArray.recycle()
    }

}