package kr.co.soogong.master.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R

class TitleEditTextView : ConstraintLayout {
    private lateinit var titleTextView: TextView
    private lateinit var textView: EditText
    private lateinit var hintTextView: TextView

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
        getAttrs(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int)
            : super(context, attributeSet) {
        initView()
        getAttrs(attributeSet, defStyle)
    }

    private fun getAttrs(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TitleEditTextView)
        setTypeArray(typedArray)
    }

    private fun initView() {
        val infService = Context.LAYOUT_INFLATER_SERVICE;
        val layoutInflater = context.getSystemService(infService) as LayoutInflater
        val v = layoutInflater.inflate(R.layout.view_title_edittextview, this, false);
        addView(v)

        titleTextView = findViewById(R.id.title)
        textView = findViewById(R.id.text)
        hintTextView = findViewById(R.id.hint_text)
    }

    private fun getAttrs(attributeSet: AttributeSet, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.TitleEditTextView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        titleTextView.text = typedArray.getString(R.styleable.TitleEditTextView_title_edit_title_text)
        hintTextView.text = typedArray.getString(R.styleable.TitleEditTextView_title_edit_hint_text)
        hintTextView.visibility =
            if (typedArray.getBoolean(R.styleable.TitleEditTextView_title_edit_hint_visible, false)) {
                View.VISIBLE
            } else {
                View.GONE
            }
        typedArray.recycle()
    }

    fun setTitleText(text: CharSequence) {
        titleTextView.text = text
    }

    fun getText(): String? {
        return textView.text.toString()
    }

    fun setHintVisible(visible: Boolean) {
        hintTextView.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}