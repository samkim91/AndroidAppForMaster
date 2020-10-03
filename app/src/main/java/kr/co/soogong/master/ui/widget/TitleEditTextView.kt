package kr.co.soogong.master.ui.widget

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import kr.co.soogong.master.databinding.ViewTitleEdittextviewBinding

class TitleEditTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleEdittextviewBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var hintVisible: Boolean = false
        set(value) {
            field = value
            binding.hintText.visibility = if (value) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    var hintText: String? = ""
        set(value) {
            field = value
            binding.hintText.text = value
        }

    var text: String? = ""
        set(value) {
            field = value
            binding.detail.setText(value, TextView.BufferType.EDITABLE)
        }
        get() {
            return binding.detail.text.toString()
        }

    fun addTextChangedListener(watcher: TextWatcher) {
        binding.detail.addTextChangedListener {
            it?.toString()
        }
    }
}