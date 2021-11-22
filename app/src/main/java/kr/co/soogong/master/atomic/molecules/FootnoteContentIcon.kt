package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewFootnoteCountIconBinding

class FootnoteContentIcon @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewFootnoteCountIconBinding.inflate(LayoutInflater.from(context), this, true)

    var footnote: String = ""
        set(value) {
            if (value.isEmpty()) return

            field = value
            binding.tvFootnote.text = value
        }

    var count: Int? = null
        set(value) {
            value?.let {
                field = it
                binding.tvCount.text = context.getString(R.string.summary_item, it.toString())
                if (it > 1000)
                    "+${context.getString(R.string.summary_item, it.toString())}"
                else
                    context.getString(R.string.summary_item, it.toString())
            }
        }

    var icon: Drawable? = null
        set(value) {
            value?.let {
                field = it
                binding.ivIcon.background = it
            }
        }
}