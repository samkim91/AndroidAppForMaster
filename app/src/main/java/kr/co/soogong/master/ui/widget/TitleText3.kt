package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTitleText3Binding

class TitleText3 @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding: ViewTitleText3Binding =
        ViewTitleText3Binding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = "$value :"
        }

    var detail: String? = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                binding.detail.setText(R.string.null_text)
            } else {
                binding.detail.text = value
            }
        }
}