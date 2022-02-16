package kr.co.soogong.master.presentation.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewLogoTitle3FootnoteBinding

class LogoTitle3Footnote @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewLogoTitle3FootnoteBinding.inflate(LayoutInflater.from(context), this, true)

    val tvTitle3: AppCompatTextView = binding.tvTitle3
    val tvFootnote: AppCompatTextView = binding.tvFootnote

    var title: String? = null
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) tvTitle3.text = value
        }

    var footnote: String? = null
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) binding.tvFootnote.text = value
        }
}