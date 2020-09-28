package kr.co.soogong.master.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTitleEdittextviewBinding

class TitleEditTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleEdittextviewBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)

        getAttrs(attributeSet)
    }

    private fun getAttrs(attributeSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TitleEditTextView)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        binding.title.text =
            typedArray.getString(R.styleable.TitleEditTextView_title_edit_title_text)
        binding.hintText.text =
            typedArray.getString(R.styleable.TitleEditTextView_title_edit_hint_text)
        binding.hintText.visibility =
            if (typedArray.getBoolean(
                    R.styleable.TitleEditTextView_title_edit_hint_visible,
                    false
                )
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }
        typedArray.recycle()
    }

    fun setTitleText(text: CharSequence) {
        binding.title.text = text
    }

    fun getText(): String? {
        return binding.text.text.toString()
    }

    fun setHintVisible(visible: Boolean) {
        binding.hintText.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        binding.text.addTextChangedListener {
            it?.toString()
        }
    }
}