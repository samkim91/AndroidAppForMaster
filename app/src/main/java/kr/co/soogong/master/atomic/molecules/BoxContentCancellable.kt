package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewBoxContentCancellableBinding

class BoxContentCancellable @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewBoxContentCancellableBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.ivIcon.setOnClickListener {
            this.isVisible = false
        }
    }

    var content: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvContent.text = it
            }
        }
}