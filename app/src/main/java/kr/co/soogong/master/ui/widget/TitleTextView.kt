package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewTitleTextviewBinding

class TitleTextView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding: ViewTitleTextviewBinding =
        ViewTitleTextviewBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var detail: String? = ""
        set(value) {
            field = value
            binding.detail.text = value
        }
}